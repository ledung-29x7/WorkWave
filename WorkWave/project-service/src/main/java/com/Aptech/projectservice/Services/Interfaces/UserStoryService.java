package com.Aptech.projectservice.Services.Interfaces;

import java.util.List;

import com.Aptech.projectservice.Dtos.Request.UserStoryRequestDto;
import com.Aptech.projectservice.Dtos.Response.UserStoryResponseDto;

public interface UserStoryService {
    public void createUserStory(UserStoryRequestDto request, String createdBy, String projectId);

    public UserStoryResponseDto getUserStoryById(Integer id);

    public void updateUserStory(Integer id, UserStoryRequestDto request, String updatedBy);

    public void deleteUserStory(Integer id);

    public List<UserStoryResponseDto> getUserStoriesByEpic(Integer epicId);

    public List<UserStoryResponseDto> getUserStoriesByUser(String assignedTo);

    public List<UserStoryResponseDto> getUserStoriesByProject(String projectId);

    public List<UserStoryResponseDto> getUserStoriesBySprint(Integer sprintId);

}
