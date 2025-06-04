package com.Aptech.projectservice.Controllers;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Aptech.projectservice.Configs.JwtTokenProvider;
import com.Aptech.projectservice.Configs.ProjectContext;
import com.Aptech.projectservice.Dtos.Request.UserStoryRequestDto;
import com.Aptech.projectservice.Dtos.Response.ApiResponse;
import com.Aptech.projectservice.Dtos.Response.UserStoryResponseDto;
import com.Aptech.projectservice.Services.Interfaces.UserStoryService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/stories")
@RequiredArgsConstructor
public class UserStoryController {
    private final UserStoryService userStoryService;
    private final JwtTokenProvider jwt;

    @PreAuthorize("hasAuthority('USER_STORY_CREATE')")
    @PostMapping()
    public ApiResponse<String> createUserStory(
            @RequestBody UserStoryRequestDto request, HttpServletRequest http) {
        String projectId = ProjectContext.getProjectId();
        String token = http.getHeader("Authorization").substring(7);
        String userId = null;
        try {
            userId = jwt.getUserIdFromToken(token);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        userStoryService.createUserStory(request, userId, projectId);
        return ApiResponse.<String>builder()
                .status("SUCCESS")
                .data("User Story created successfully")
                .build();
    }

    @PreAuthorize("hasAuthority('USER_STORY_VIEW')")
    @GetMapping("/{id}")
    public ApiResponse<UserStoryResponseDto> getUserStoryById(@PathVariable("id") Integer id) {
        UserStoryResponseDto story = userStoryService.getUserStoryById(id);
        return ApiResponse.<UserStoryResponseDto>builder()
                .status("SUCCESS")
                .data(story)
                .build();
    }

    @PreAuthorize("hasAuthority('USER_STORY_UPDATE')")
    @PutMapping("/{id}")
    public ApiResponse<String> updateUserStory(
            @PathVariable("id") Integer id,
            @RequestBody UserStoryRequestDto request, HttpServletRequest http) {
        String token = http.getHeader("Authorization").substring(7);
        String userId = null;
        try {
            userId = jwt.getUserIdFromToken(token);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        userStoryService.updateUserStory(id, request, userId);
        return ApiResponse.<String>builder()
                .status("SUCCESS")
                .data("User Story updated successfully")
                .build();
    }

    @PreAuthorize("hasAuthority('USER_STORY_DELETE')")
    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteUserStory(@PathVariable("id") Integer id) {
        userStoryService.deleteUserStory(id);
        return ApiResponse.<String>builder()
                .status("SUCCESS")
                .data("User Story deleted successfully")
                .build();
    }

    @PreAuthorize("hasAuthority('USER_STORY_VIEW')")
    @GetMapping("/{epicId}/epic")
    public ApiResponse<List<UserStoryResponseDto>> getUserStoriesByEpic(@PathVariable("epicId") Integer epicId) {
        List<UserStoryResponseDto> stories = userStoryService.getUserStoriesByEpic(epicId);
        return ApiResponse.<List<UserStoryResponseDto>>builder()
                .status("SUCCESS")
                .data(stories)
                .build();
    }

    @PreAuthorize("hasAuthority('USER_STORY_VIEW')")
    @GetMapping("/assigned")
    public ApiResponse<List<UserStoryResponseDto>> getUserStoriesByUser(HttpServletRequest http) {
        String token = http.getHeader("Authorization").substring(7);
        String userId = null;
        try {
            userId = jwt.getUserIdFromToken(token);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        List<UserStoryResponseDto> stories = userStoryService.getUserStoriesByUser(userId);
        return ApiResponse.<List<UserStoryResponseDto>>builder()
                .status("SUCCESS")
                .data(stories)
                .build();
    }

    @PreAuthorize("hasAuthority('USER_STORY_VIEW')")
    @GetMapping("/project")
    public ApiResponse<List<UserStoryResponseDto>> getUserStoriesByProject() {
        String projectId = ProjectContext.getProjectId();
        List<UserStoryResponseDto> stories = userStoryService.getUserStoriesByProject(projectId);
        return ApiResponse.<List<UserStoryResponseDto>>builder()
                .status("SUCCESS")
                .data(stories)
                .build();
    }

}