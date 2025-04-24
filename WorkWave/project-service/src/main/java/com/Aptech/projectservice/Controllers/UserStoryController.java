package com.Aptech.projectservice.Controllers;

import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Aptech.projectservice.Dtos.Request.UserStoryRequestDto;
import com.Aptech.projectservice.Dtos.Response.ApiResponse;
import com.Aptech.projectservice.Dtos.Response.UserStoryResponseDto;
import com.Aptech.projectservice.Services.Interfaces.UserStoryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/stories")
@RequiredArgsConstructor
public class UserStoryController {
    private final UserStoryService userStoryService;

    @PostMapping("/{epicId}/epic")
    public ApiResponse<String> createUserStory(
            @PathVariable("epicId") Integer epicId,
            @RequestBody UserStoryRequestDto request) {
        userStoryService.createUserStory(epicId, request);
        return ApiResponse.<String>builder()
                .status("SUCCESS")
                .data("User Story created successfully")
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<UserStoryResponseDto> getUserStoryById(@PathVariable("id") Integer id) {
        UserStoryResponseDto story = userStoryService.getUserStoryById(id);
        return ApiResponse.<UserStoryResponseDto>builder()
                .status("SUCCESS")
                .data(story)
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<String> updateUserStory(
            @PathVariable("id") Integer id,
            @RequestBody UserStoryRequestDto request) {
        userStoryService.updateUserStory(id, request);
        return ApiResponse.<String>builder()
                .status("SUCCESS")
                .data("User Story updated successfully")
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteUserStory(@PathVariable("id") Integer id) {
        userStoryService.deleteUserStory(id);
        return ApiResponse.<String>builder()
                .status("SUCCESS")
                .data("User Story deleted successfully")
                .build();
    }

    @GetMapping("/{epicId}/epic")
    public ApiResponse<List<UserStoryResponseDto>> getUserStoriesByEpic(@PathVariable("epicId") Integer epicId) {
        List<UserStoryResponseDto> stories = userStoryService.getUserStoriesByEpic(epicId);
        return ApiResponse.<List<UserStoryResponseDto>>builder()
                .status("SUCCESS")
                .data(stories)
                .build();
    }
}