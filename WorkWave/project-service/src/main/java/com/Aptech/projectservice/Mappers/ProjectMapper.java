package com.Aptech.projectservice.Mappers;

import org.mapstruct.Mapper;

import com.Aptech.projectservice.Dtos.Request.ProjectCreationRequest;
import com.Aptech.projectservice.Dtos.Request.ProjectDto;
import com.Aptech.projectservice.Dtos.Response.ProjectResponse;
import com.Aptech.projectservice.Entitys.Project;

@Mapper(componentModel = "Spring")
public interface ProjectMapper {
    Project toProject(ProjectCreationRequest request);

    ProjectResponse toProjectResponse(Project project);

    ProjectDto toDto(Project entity);

    Project toEntity(ProjectDto dto);
}
