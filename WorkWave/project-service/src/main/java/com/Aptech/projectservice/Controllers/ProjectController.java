package com.Aptech.projectservice.Controllers;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.Aptech.projectservice.Dtos.Request.ProjectDto;
import com.Aptech.projectservice.Dtos.Response.ApiResponse;
import com.Aptech.projectservice.Services.Interfaces.ProjectService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/project")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Slf4j
public class ProjectController {
    ProjectService projectService;

    // @PreAuthorize("hasAuthority('PROJECT_CREATE')")
    // @PostMapping
    // public ApiResponse<String> createProject(@RequestBody ProjectDto dto) {
    // projectService.createProject(dto);
    // return ApiResponse.<String>builder()
    // .status("SUCCESS")
    // .data("Project created successfully")
    // .build();
    // }

    @PreAuthorize("hasAuthority('PROJECT_VIEW')")
    @GetMapping("/{id}")
    public ApiResponse<ProjectDto> getProjectById(@PathVariable("id") String id) {
        ProjectDto project = projectService.getProjectById(id);
        return ApiResponse.<ProjectDto>builder()
                .status("SUCCESS")
                .data(project)
                .build();
    }

    // @PreAuthorize("hasAuthority('PROJECT_UPDATE')")
    // @PutMapping("/{id}")
    // public ApiResponse<String> updateProject(@PathVariable("id") String id,
    // @RequestBody ProjectDto dto) {
    // projectService.updateProject(id, dto);
    // return ApiResponse.<String>builder()
    // .status("SUCCESS")
    // .data("Project updated successfully")
    // .build();
    // }

    @PreAuthorize("hasAuthority('PROJECT_DELETE')")
    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteProject(@PathVariable("id") String id) {
        projectService.deleteProject(id);
        return ApiResponse.<String>builder()
                .status("SUCCESS")
                .data("Project deleted successfully")
                .build();
    }

    @GetMapping
    public ApiResponse<List<ProjectDto>> getAllProjects() {
        List<ProjectDto> projects = projectService.getAllProjects();
        return ApiResponse.<List<ProjectDto>>builder()
                .status("SUCCESS")
                .data(projects)
                .build();
    }
}
