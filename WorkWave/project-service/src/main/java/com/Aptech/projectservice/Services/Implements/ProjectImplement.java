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
import com.Aptech.projectservice.event.KafkaProducerService;
import com.aptech.common.event.project.ProjectCreatedEvent;
import com.aptech.common.event.project.ProjectDeletedEvent;
import com.aptech.common.event.project.ProjectUpdatedEvent;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class ProjectImplement implements ProjectService {
    ProjectRepository repository;
    ProjectMapper mapper;
    KafkaProducerService kafkaProducerService;

    @Override
    public void createProject(ProjectCreatedEvent dto) {
        repository.createProject(dto.getProjectId(), dto.getName(), dto.getDescription(), dto.getStartDate(),
                dto.getEndDate(),
                dto.getStatusId(), dto.getCreatedBy());
    }

    @Override
    public ProjectDto getProjectById(String id) {
        Project project = repository.findProjectById(id);
        return mapper.toDto(project);
    }

    @Override
    public void updateProject(ProjectUpdatedEvent dto) {
        repository.updateProject(dto.getProjectId(), dto.getName(), dto.getDescription(), dto.getStartDate(),
                dto.getEndDate(),
                dto.getStatusId(), dto.getUpdatedBy());
    }

    @Override
    public void deleteProject(String id) {
        repository.deleteProject(id);
        ProjectDeletedEvent event = new ProjectDeletedEvent(id);
        kafkaProducerService.send("project-events", event);
    }

    @Override
    public List<ProjectDto> getAllProjects() {
        return repository.findAllProjects().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }
}
