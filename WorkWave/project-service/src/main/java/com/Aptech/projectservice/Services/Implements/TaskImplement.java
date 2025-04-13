package com.Aptech.projectservice.Services.Implements;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.Aptech.projectservice.Dtos.Request.TaskRequestDto;
import com.Aptech.projectservice.Dtos.Response.TaskResponseDto;
import com.Aptech.projectservice.Entitys.Task;
import com.Aptech.projectservice.Mappers.TaskMapper;
import com.Aptech.projectservice.Repositorys.TaskRepository;
import com.Aptech.projectservice.Services.Interfaces.TaskService;

import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class TaskImplement implements TaskService {
    TaskRepository taskRepository;
    TaskMapper taskMapper;

    @Transactional
    public void createTask(Integer storyId, TaskRequestDto request) {
        taskRepository.createTask(
                storyId,
                request.getAssignedTo(),
                request.getName(),
                request.getDescription(),
                request.getStatusId(),
                request.getEstimatedHours(),
                request.getCreatedBy());
    }

    public TaskResponseDto getTaskById(Integer id) {
        Task task = taskRepository.getTaskById(id);
        return taskMapper.toDto(task);
    }

    @Transactional
    public void updateTask(Integer id, TaskRequestDto request) {
        taskRepository.updateTask(
                id,
                request.getAssignedTo(),
                request.getName(),
                request.getDescription(),
                request.getStatusId(),
                request.getEstimatedHours(),
                request.getLoggedHours(),
                request.getRemainingHours(),
                request.getUpdatedBy());
    }

    @Transactional
    public void deleteTask(Integer id) {
        taskRepository.deleteTask(id);
    }

    public List<TaskResponseDto> getTasksByStory(Integer storyId) {
        List<Task> tasks = taskRepository.getTasksByStoryId(storyId);
        return tasks.stream().map(taskMapper::toDto).collect(Collectors.toList());
    }
}
