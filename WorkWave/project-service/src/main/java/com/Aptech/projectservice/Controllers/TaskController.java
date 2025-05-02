package com.Aptech.projectservice.Controllers;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Aptech.projectservice.Dtos.Request.TaskRequestDto;
import com.Aptech.projectservice.Dtos.Response.ApiResponse;
import com.Aptech.projectservice.Dtos.Response.TaskResponseDto;
import com.Aptech.projectservice.Entitys.Task;
import com.Aptech.projectservice.Services.Interfaces.TaskService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/tasks")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Slf4j
public class TaskController {

    TaskService taskService;

    @PostMapping("/{storyId}/story")
    public ApiResponse<String> createTask(
            @PathVariable("storyId") Integer storyId,
            @RequestBody TaskRequestDto request) {
        taskService.createTask(storyId, request);
        return ApiResponse.<String>builder()
                .status("SUCCESS")
                .data("Task created successfully")
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<TaskResponseDto> getTaskById(@PathVariable("id") Integer id) {
        TaskResponseDto task = taskService.getTaskById(id);
        return ApiResponse.<TaskResponseDto>builder()
                .status("SUCCESS")
                .data(task)
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<String> updateTask(
            @PathVariable("id") Integer id,
            @RequestBody TaskRequestDto request) {
        taskService.updateTask(id, request);
        return ApiResponse.<String>builder()
                .status("SUCCESS")
                .data("Task updated successfully")
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteTask(@PathVariable("id") Integer id) {
        taskService.deleteTask(id);
        return ApiResponse.<String>builder()
                .status("SUCCESS")
                .data("Task deleted successfully")
                .build();
    }

    @GetMapping("/{storyId}/story")
    public ApiResponse<List<TaskResponseDto>> getTasksByStory(@PathVariable("storyId") Integer storyId) {
        List<TaskResponseDto> tasks = taskService.getTasksByStory(storyId);
        return ApiResponse.<List<TaskResponseDto>>builder()
                .status("SUCCESS")
                .data(tasks)
                .build();
    }

    @GetMapping("/search")
    public ApiResponse<List<Task>> getTasksByAssignedToAndProjectId(
            @RequestParam(required = false) String assignedTo,
            @RequestParam(required = false) String projectId) {
        List<Task> tasks = taskService.getTasksByAssignedToAndProjectId(assignedTo, projectId);
        return ApiResponse.<List<Task>>builder().status("SUCCESS").data(tasks).build();
    }
}
