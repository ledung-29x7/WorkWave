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

import com.Aptech.projectservice.Dtos.Request.UserStoryRequestDto;
import com.Aptech.projectservice.Dtos.Response.UserStoryResponseDto;
import com.Aptech.projectservice.Services.Interfaces.UserStoryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/stories")
@RequiredArgsConstructor
public class UserStoryController {
    private final UserStoryService userStoryService;

    @PostMapping("/{epicId}/epic")
    public ResponseEntity<String> createUserStory(
            @PathVariable("epicId") Integer epicId,
            @RequestBody UserStoryRequestDto request) {
        userStoryService.createUserStory(epicId, request);
        return ResponseEntity.ok("User Story created successfully");
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserStoryResponseDto> getUserStoryById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(userStoryService.getUserStoryById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateUserStory(
            @PathVariable("id") Integer id,
            @RequestBody UserStoryRequestDto request) {
        userStoryService.updateUserStory(id, request);
        return ResponseEntity.ok("User Story updated successfully");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUserStory(@PathVariable("id") Integer id) {
        userStoryService.deleteUserStory(id);
        return ResponseEntity.ok("User Story deleted successfully");
    }

    @GetMapping("/{epicId}/epic")
    public ResponseEntity<List<UserStoryResponseDto>> getUserStoriesByEpic(@PathVariable("epicId") Integer epicId) {
        return ResponseEntity.ok(userStoryService.getUserStoriesByEpic(epicId));
    }
}