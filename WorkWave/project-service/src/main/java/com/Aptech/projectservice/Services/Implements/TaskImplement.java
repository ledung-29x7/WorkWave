package com.Aptech.projectservice.Services.Implements;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.Aptech.projectservice.Dtos.Request.TaskRequestDto;
import com.Aptech.projectservice.Dtos.Response.TaskResponseDto;
import com.Aptech.projectservice.Entitys.Task;
import com.Aptech.projectservice.Mappers.TaskMapper;
import com.Aptech.projectservice.Repositorys.TaskRepository;
import com.Aptech.projectservice.Repositorys.TaskStatusRepository;
import com.Aptech.projectservice.Repositorys.UserLookupRepository;
import com.Aptech.projectservice.Services.Interfaces.TaskService;
import com.Aptech.projectservice.event.KafkaProducerService;
import com.aptech.common.event.email.NotificationEvent;
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
    UserLookupRepository userLookupRepository;
    TaskStatusRepository taskStatusRepository;

    @Transactional
    public void createTask(TaskRequestDto request, String createdBy) {
        taskRepository.createTask(
                request.getStoryId(),
                request.getAssignedTo(),
                request.getName(),
                request.getDescription(),
                request.getStatusId(),
                request.getEstimatedHours(),
                createdBy);
        Integer taskId = taskRepository.getLatestCreatedTaskId(request.getStoryId(), createdBy);
        TaskCreatedEvent event = new TaskCreatedEvent(
                taskId,
                request.getStoryId(),
                request.getName(),
                request.getDescription(),
                request.getStatusId(),
                request.getEstimatedHours(),
                request.getLoggedHours(),
                request.getRemainingHours(),
                request.getAssignedTo(),
                createdBy);

        kafkaProducerService.send("task-events", event);

    }

    public TaskResponseDto getTaskById(Integer id) {
        Task task = taskRepository.getTaskById(id);
        return taskMapper.toDto(task);
    }

    @Transactional
    public void updateTask(Integer id, TaskRequestDto request, String updatedBy) {
        taskRepository.updateTask(
                id,
                request.getAssignedTo(),
                request.getName(),
                request.getDescription(),
                request.getStatusId(),
                request.getEstimatedHours(),
                request.getLoggedHours(),
                request.getRemainingHours(),
                updatedBy);
        TaskUpdatedEvent event = new TaskUpdatedEvent(
                id,
                request.getName(),
                request.getDescription(),
                request.getStatusId(),
                request.getEstimatedHours(),
                request.getLoggedHours(),
                request.getRemainingHours(),
                request.getAssignedTo(),
                updatedBy);

        Map<String, String> data = new HashMap<>();
        data.put("taskName", request.getName());
        data.put("updatedBy", userLookupRepository.getUserById(updatedBy).getUserName());
        data.put("estimatedHours", String.valueOf(request.getEstimatedHours()));
        data.put("Status", taskStatusRepository.getStatusById(request.getStatusId()).getStatusName());

        NotificationEvent event_email = new NotificationEvent(
                "TASK_UPDATED",
                userLookupRepository.getUserById(request.getAssignedTo()).getEmail(), // email
                "d-c4f25b887c3349ce9e0ac82dc8c755db", // template ID trên SendGrid
                data);

        kafkaProducerService.send("notification-events", event_email);

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
