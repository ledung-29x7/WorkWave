package com.Aptech.projectservice.Services.Implements;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.Aptech.projectservice.Dtos.Request.UserStoryRequestDto;
import com.Aptech.projectservice.Dtos.Response.UserStoryResponseDto;
import com.Aptech.projectservice.Entitys.UserStory;
import com.Aptech.projectservice.Mappers.UserStoryMapper;
import com.Aptech.projectservice.Repositorys.UserStoryRepository;
import com.Aptech.projectservice.Services.Interfaces.UserStoryService;
import com.Aptech.projectservice.event.KafkaProducerService;
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

    @Transactional
    public void createUserStory(UserStoryRequestDto request, String createdBy) {
        userStoryRepository.createUserStory(
                request.getEpicId(),
                request.getSprintId(),
                request.getName(),
                request.getDescription(),
                request.getPriorityId(),
                request.getStatusId(),
                createdBy);

        Integer storyId = userStoryRepository.getLatestCreatedStoryId(createdBy);

        UserStoryCreatedEvent event = new UserStoryCreatedEvent(
                storyId,
                request.getName(),
                request.getDescription(),
                request.getEpicId(),
                request.getPriorityId(),
                request.getStatusId(),
                createdBy);

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
                request.getStatusId(),
                updatedBy);

        UserStoryUpdatedEvent event = new UserStoryUpdatedEvent(
                id,
                request.getName(),
                request.getDescription(),
                request.getEpicId(),
                request.getPriorityId(),
                request.getStatusId());

        kafkaProducerService.send("userstory-events", event);
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
}
