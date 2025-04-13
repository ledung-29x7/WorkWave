package com.Aptech.projectservice.Services.Implements;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.Aptech.projectservice.Dtos.Request.ProjectDto;
import com.Aptech.projectservice.Entitys.Project;
import com.Aptech.projectservice.Mappers.ProjectMapper;
import com.Aptech.projectservice.Repositorys.ProjectRepository;
import com.Aptech.projectservice.Services.Interfaces.ProjectService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class ProjectImplement implements ProjectService {
    ProjectRepository repository;
    ProjectMapper mapper;

    @Override
    public void createProject(ProjectDto dto) {
        String projectId = UUID.randomUUID().toString();
        repository.createProject(projectId, dto.getName(), dto.getDescription(), dto.getStartDate(), dto.getEndDate(),
                dto.getStatusId(), dto.getCreatedBy(), dto.getUpdatedBy());
    }

    @Override
    public ProjectDto getProjectById(String id) {
        Project project = repository.findProjectById(id);
        return mapper.toDto(project);
    }

    @Override
    public void updateProject(String id, ProjectDto dto) {
        repository.updateProject(id, dto.getName(), dto.getDescription(), dto.getStartDate(), dto.getEndDate(),
                dto.getStatusId(), dto.getUpdatedBy());
    }

    @Override
    public void deleteProject(String id) {
        repository.deleteProject(id);
    }

    @Override
    public List<ProjectDto> getAllProjects() {
        return repository.findAllProjects().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }
}
