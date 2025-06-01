package com.Aptech.projectservice.Controllers;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.Aptech.projectservice.Configs.JwtTokenProvider;
import com.Aptech.projectservice.Configs.ProjectContext;
import com.Aptech.projectservice.Dtos.Request.SprintRequestDto;
import com.Aptech.projectservice.Dtos.Response.ApiResponse;
import com.Aptech.projectservice.Dtos.Response.SprintResponseDto;
import com.Aptech.projectservice.Services.Interfaces.SprintService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/sprint")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Slf4j
public class SprintController {
    SprintService sprintService;
    JwtTokenProvider jwt;

    @PreAuthorize("hasAuthority('SPRINT_VIEW')")
    @GetMapping()
    public ApiResponse<List<SprintResponseDto>> getSprints() {
        String projectId = ProjectContext.getProjectId();
        List<SprintResponseDto> sprints = sprintService.getSprintsByProject(projectId);
        return ApiResponse.<List<SprintResponseDto>>builder()
                .status("SUCCESS")
                .data(sprints)
                .build();
    }

    @PreAuthorize("hasAuthority('SPRINT_VIEW')")
    @GetMapping("/{id}")
    public ApiResponse<SprintResponseDto> getSprintById(@PathVariable("id") Integer id) {
        SprintResponseDto sprint = sprintService.getSprintById(id);
        return ApiResponse.<SprintResponseDto>builder()
                .status("SUCCESS")
                .data(sprint)
                .build();
    }

    @PreAuthorize("hasAuthority('SPRINT_CREATE')")
    @PostMapping()
    public ApiResponse<String> createSprint(
            @RequestBody SprintRequestDto dto, HttpServletRequest http) {
        String projectId = ProjectContext.getProjectId();
        String token = http.getHeader("Authorization").substring(7);
        String userId = null;
        try {
            userId = jwt.getUserIdFromToken(token);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        sprintService.createSprint(projectId, dto, userId);
        return ApiResponse.<String>builder()
                .status("SUCCESS")
                .data("Sprint created successfully")
                .build();
    }

    @PreAuthorize("hasAuthority('SPRINT_UPDATE')")
    @PutMapping("/{id}")
    public ApiResponse<String> updateSprint(
            @PathVariable("id") Integer id,
            @RequestBody SprintRequestDto dto, HttpServletRequest http) {
        String token = http.getHeader("Authorization").substring(7);
        String userId = null;
        try {
            userId = jwt.getUserIdFromToken(token);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        sprintService.updateSprint(id, dto, userId);
        return ApiResponse.<String>builder()
                .status("SUCCESS")
                .data("Sprint updated successfully")
                .build();
    }

    @PreAuthorize("hasAuthority('SPRINT_DELETE')")
    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteSprint(@PathVariable("id") Integer id) {
        sprintService.deleteSprint(id);
        return ApiResponse.<String>builder()
                .status("SUCCESS")
                .data("Sprint deleted successfully")
                .build();
    }
}
