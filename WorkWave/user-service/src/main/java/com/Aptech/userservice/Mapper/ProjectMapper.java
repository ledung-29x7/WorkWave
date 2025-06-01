package com.Aptech.userservice.Mapper;

import org.mapstruct.Mapper;

import com.Aptech.userservice.Dtos.Request.ProjectRequest;
import com.Aptech.userservice.Dtos.Response.ProjectResponse;
import com.Aptech.userservice.Entitys.Project;

@Mapper(componentModel = "spring")
public interface ProjectMapper {
    Project toEntity(ProjectRequest dto);

    ProjectResponse toDto(Project entity);
}
