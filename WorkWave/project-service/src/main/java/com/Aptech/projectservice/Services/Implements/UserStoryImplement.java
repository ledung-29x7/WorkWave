package com.Aptech.projectservice.Services.Implements;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.Aptech.projectservice.Dtos.Request.UserStoryRequestDto;
import com.Aptech.projectservice.Dtos.Response.UserStoryResponseDto;
import com.Aptech.projectservice.Entitys.UserStory;
import com.Aptech.projectservice.Mappers.UserStoryMapper;
import com.Aptech.projectservice.Repositorys.PriorityRepository;
import com.Aptech.projectservice.Repositorys.UserLookupRepository;
import com.Aptech.projectservice.Repositorys.UserStoryRepository;
import com.Aptech.projectservice.Repositorys.UserStoryStatusRepository;
import com.Aptech.projectservice.Services.Interfaces.UserStoryService;
import com.Aptech.projectservice.event.KafkaProducerService;
import com.aptech.common.event.email.NotificationEvent;
import com.aptech.common.event.project.UserStoryCreatedEvent;
import com.aptech.common.event.project.UserStoryDeletedEvent;
import com.aptech.common.event.project.UserStoryUpdatedEvent;

import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class UserStoryImplement implements UserStoryService {
    UserStoryRepository userStoryRepository;
    UserStoryMapper userStoryMapper;
    KafkaProducerService kafkaProducerService;
    PriorityRepository priorityRepository;
    UserLookupRepository userLookupRepository;
    UserStoryStatusRepository userStoryStatusRepository;

    @Transactional
    public void createUserStory(UserStoryRequestDto request, String createdBy, String projectId) {
        userStoryRepository.createUserStory(
                request.getEpicId(),
                request.getSprintId(),
                request.getName(),
                request.getDescription(),
                request.getPriorityId(),
                request.getAssignedTo(),
                request.getStatusId(),
                createdBy, projectId);

        Integer storyId = userStoryRepository.getLatestCreatedStoryId(createdBy);

        UserStoryCreatedEvent event = new UserStoryCreatedEvent(
                storyId,
                request.getName(),
                request.getDescription(),
                request.getEpicId(),
                request.getPriorityId(),
                request.getAssignedTo(),
                request.getStatusId(),
                createdBy, projectId);

        kafkaProducerService.send("userstory-events", event);
    }

    public UserStoryResponseDto getUserStoryById(Integer id) {
        UserStory userStory = userStoryRepository.getUserStoryById(id);
        return userStoryMapper.toDto(userStory);
    }

    @Transactional
    public void updateUserStory(Integer id, UserStoryRequestDto request, String updatedBy) {
        userStoryRepository.updateUserStory(
                id,
                request.getEpicId(),
                request.getSprintId(),
                request.getName(),
                request.getDescription(),
                request.getPriorityId(),
                request.getAssignedTo(),
                request.getStatusId(),
                updatedBy);

        UserStoryUpdatedEvent event = new UserStoryUpdatedEvent(
                id,
                request.getName(),
                request.getDescription(),
                request.getEpicId(),
                request.getPriorityId(),
                request.getAssignedTo(),
                request.getStatusId());

        // Gửi event userstory-events luôn
        kafkaProducerService.send("userstory-events", event);

        // Lấy thông tin user được assign
        var user = userLookupRepository.getUserById(request.getAssignedTo());
        if (user != null) {
            Map<String, String> data = new HashMap<>();
            data.put("storyName", request.getName());
            data.put("priority", priorityRepository.getPriorityById(request.getPriorityId()).getPriorityName());
            data.put("Status", userStoryStatusRepository.getStatusById(request.getStatusId()).getStatusName());

            NotificationEvent event_email = new NotificationEvent(
                    "USERSTORY_UPDATED",
                    user.getEmail(),
                    "d-f2d205a6bd714f228a27463ecb155700", // template ID
                    data);

            kafkaProducerService.send("notification-events", event_email);
        }
    }

    @Transactional
    public void deleteUserStory(Integer id) {
        userStoryRepository.deleteUserStory(id);
        UserStoryDeletedEvent event = new UserStoryDeletedEvent(id);
        kafkaProducerService.send("userstory-events", event);

    }

    public List<UserStoryResponseDto> getUserStoriesByEpic(Integer epicId) {
        List<UserStory> stories = userStoryRepository.getUserStoriesByEpicId(epicId);
        return stories.stream().map(userStoryMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public List<UserStoryResponseDto> getUserStoriesByUser(String assignedTo) {
        List<UserStory> stories = userStoryRepository.getUserStoriesByUserId(assignedTo);
        return stories.stream().map(userStoryMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public List<UserStoryResponseDto> getUserStoriesByProject(String projectId) {
        List<UserStory> stories = userStoryRepository.getUserStoriesByProjectId(projectId);
        return stories.stream().map(userStoryMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public List<UserStoryResponseDto> getUserStoriesBySprint(Integer sprintId) {
        List<UserStory> stories = userStoryRepository.getUserStoriesBySprintId(sprintId);
        return stories.stream().map(userStoryMapper::toDto).collect(Collectors.toList());
    }
}
