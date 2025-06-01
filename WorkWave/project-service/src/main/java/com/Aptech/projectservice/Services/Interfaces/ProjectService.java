package com.Aptech.projectservice.Services.Interfaces;

import java.util.List;

import com.Aptech.projectservice.Dtos.Request.ProjectDto;
import com.aptech.common.event.project.ProjectCreatedEvent;
import com.aptech.common.event.project.ProjectUpdatedEvent;

public interface ProjectService {
    void createProject(ProjectCreatedEvent dto);

    ProjectDto getProjectById(String id);

    void updateProject(ProjectUpdatedEvent dto);

    void deleteProject(String id);

    List<ProjectDto> getAllProjects();
}
