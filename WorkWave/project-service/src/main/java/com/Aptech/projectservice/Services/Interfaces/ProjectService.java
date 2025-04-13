package com.Aptech.projectservice.Services.Interfaces;

import java.util.List;

import com.Aptech.projectservice.Dtos.Request.ProjectDto;

public interface ProjectService {
    void createProject(ProjectDto dto);

    ProjectDto getProjectById(String id);

    void updateProject(String id, ProjectDto dto);

    void deleteProject(String id);

    List<ProjectDto> getAllProjects();
}
