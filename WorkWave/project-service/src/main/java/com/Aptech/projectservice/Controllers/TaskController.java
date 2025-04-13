package com.Aptech.projectservice.Controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Aptech.projectservice.Dtos.Request.TaskRequestDto;
import com.Aptech.projectservice.Dtos.Response.TaskResponseDto;
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
    public ResponseEntity<String> createTask(
            @PathVariable("storyId") Integer storyId,
            @RequestBody TaskRequestDto request) {
        taskService.createTask(storyId, request);
        return ResponseEntity.ok("Task created successfully");
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskResponseDto> getTaskById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(taskService.getTaskById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateTask(
            @PathVariable("id") Integer id,
            @RequestBody TaskRequestDto request) {
        taskService.updateTask(id, request);
        return ResponseEntity.ok("Task updated successfully");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTask(@PathVariable("id") Integer id) {
        taskService.deleteTask(id);
        return ResponseEntity.ok("Task deleted successfully");
    }

    @GetMapping("/{storyId}/story")
    public ResponseEntity<List<TaskResponseDto>> getTasksByStory(@PathVariable("storyId") Integer storyId) {
        return ResponseEntity.ok(taskService.getTasksByStory(storyId));
    }
}
