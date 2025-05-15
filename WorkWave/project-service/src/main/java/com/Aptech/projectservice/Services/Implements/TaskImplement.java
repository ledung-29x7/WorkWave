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
import com.Aptech.projectservice.event.KafkaProducerService;
import com.aptech.common.event.project.TaskCreatedEvent;
import com.aptech.common.event.project.TaskDeletedEvent;
import com.aptech.common.event.project.TaskUpdatedEvent;

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
    KafkaProducerService kafkaProducerService;

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
        Integer taskId = taskRepository.getLatestCreatedTaskId(storyId, request.getCreatedBy());
        TaskCreatedEvent event = new TaskCreatedEvent(
                taskId,
                storyId,
                request.getName(),
                request.getDescription(),
                request.getStatusId(),
                request.getEstimatedHours(),
                request.getLoggedHours(),
                request.getRemainingHours(),
                request.getAssignedTo(),
                request.getCreatedBy());

        kafkaProducerService.send("task-events", event);

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
        TaskUpdatedEvent event = new TaskUpdatedEvent(
                id,
                request.getName(),
                request.getDescription(),
                request.getStatusId(),
                request.getEstimatedHours(),
                request.getLoggedHours(),
                request.getRemainingHours(),
                request.getAssignedTo(),
                request.getUpdatedBy());

        kafkaProducerService.send("task-events", event);
    }

    @Transactional
    public void deleteTask(Integer id) {
        taskRepository.deleteTask(id);
        TaskDeletedEvent event = new TaskDeletedEvent(id);
        kafkaProducerService.send("task-events", event);
    }

    public List<TaskResponseDto> getTasksByStory(Integer storyId) {
        List<Task> tasks = taskRepository.getTasksByStoryId(storyId);
        return tasks.stream().map(taskMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public List<Task> getTasksByAssignedToAndProjectId(String assignedTo, String projectId) {
        return taskRepository.getTaskByAssignedToAndProjectId(assignedTo, projectId);
    }
}
