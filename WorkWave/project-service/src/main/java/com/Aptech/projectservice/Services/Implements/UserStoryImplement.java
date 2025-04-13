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

    @Transactional
    public void createUserStory(Integer epicId, UserStoryRequestDto request) {
        userStoryRepository.createUserStory(
                epicId,
                request.getSprintId(),
                request.getName(),
                request.getDescription(),
                request.getPriorityId(),
                request.getStatusId(),
                request.getCreatedBy(),
                request.getUpdatedBy());
    }

    public UserStoryResponseDto getUserStoryById(Integer id) {
        UserStory userStory = userStoryRepository.getUserStoryById(id);
        return userStoryMapper.toDto(userStory);
    }

    @Transactional
    public void updateUserStory(Integer id, UserStoryRequestDto request) {
        userStoryRepository.updateUserStory(
                id,
                request.getSprintId(),
                request.getName(),
                request.getDescription(),
                request.getPriorityId(),
                request.getStatusId(),
                request.getUpdatedBy());
    }

    @Transactional
    public void deleteUserStory(Integer id) {
        userStoryRepository.deleteUserStory(id);
    }

    public List<UserStoryResponseDto> getUserStoriesByEpic(Integer epicId) {
        List<UserStory> stories = userStoryRepository.getUserStoriesByEpicId(epicId);
        return stories.stream().map(userStoryMapper::toDto).collect(Collectors.toList());
    }
}
