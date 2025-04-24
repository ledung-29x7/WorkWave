package com.Aptech.projectservice.Services.Interfaces;

import java.util.List;

import com.Aptech.projectservice.Dtos.Request.TaskRequestDto;
import com.Aptech.projectservice.Dtos.Response.TaskResponseDto;
import com.Aptech.projectservice.Entitys.Task;

public interface TaskService {
    public void createTask(Integer storyId, TaskRequestDto request);

    public TaskResponseDto getTaskById(Integer id);

    public void updateTask(Integer id, TaskRequestDto request);

    public void deleteTask(Integer id);

    public List<TaskResponseDto> getTasksByStory(Integer storyId);

    List<Task> getTasksByAssignedToAndProjectId(String assignedTo, String projectId);
}
