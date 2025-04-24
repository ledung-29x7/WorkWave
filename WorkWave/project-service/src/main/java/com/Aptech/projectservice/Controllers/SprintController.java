package com.Aptech.projectservice.Controllers;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.Aptech.projectservice.Dtos.Request.SprintRequestDto;
import com.Aptech.projectservice.Dtos.Response.ApiResponse;
import com.Aptech.projectservice.Dtos.Response.SprintResponseDto;
import com.Aptech.projectservice.Services.Interfaces.SprintService;

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

    @GetMapping("/{projectId}/project")
    public ApiResponse<List<SprintResponseDto>> getSprints(@PathVariable("projectId") String projectId) {
        List<SprintResponseDto> sprints = sprintService.getSprintsByProject(projectId);
        return ApiResponse.<List<SprintResponseDto>>builder()
                .status("SUCCESS")
                .data(sprints)
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<SprintResponseDto> getSprintById(@PathVariable("id") Integer id) {
        SprintResponseDto sprint = sprintService.getSprintById(id);
        return ApiResponse.<SprintResponseDto>builder()
                .status("SUCCESS")
                .data(sprint)
                .build();
    }

    @PostMapping("/{projectId}/project")
    public ApiResponse<String> createSprint(
            @PathVariable("projectId") String projectId,
            @RequestBody SprintRequestDto dto) {
        sprintService.createSprint(projectId, dto);
        return ApiResponse.<String>builder()
                .status("SUCCESS")
                .data("Sprint created successfully")
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<String> updateSprint(
            @PathVariable("id") Integer id,
            @RequestBody SprintRequestDto dto) {
        sprintService.updateSprint(id, dto);
        return ApiResponse.<String>builder()
                .status("SUCCESS")
                .data("Sprint updated successfully")
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteSprint(@PathVariable("id") Integer id) {
        sprintService.deleteSprint(id);
        return ApiResponse.<String>builder()
                .status("SUCCESS")
                .data("Sprint deleted successfully")
                .build();
    }
}
