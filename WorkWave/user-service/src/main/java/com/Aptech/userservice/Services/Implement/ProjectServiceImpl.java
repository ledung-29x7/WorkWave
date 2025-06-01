package com.Aptech.userservice.Services.Implement;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.Aptech.userservice.Dtos.Request.ProjectRequest;
import com.Aptech.userservice.Dtos.Request.ProjectUpdateRequest;
import com.Aptech.userservice.Dtos.Response.ProjectResponse;
import com.Aptech.userservice.Entitys.Project;
import com.Aptech.userservice.Exceptions.AppException;
import com.Aptech.userservice.Exceptions.ErrorCode;
import com.Aptech.userservice.Mapper.ProjectMapper;
import com.Aptech.userservice.Repositorys.ProjectRepository;
import com.Aptech.userservice.Services.Interfaces.IProjectService;
import com.Aptech.userservice.event.KafkaProducerService;
import com.aptech.common.event.project.ProjectCreatedEvent;
import com.aptech.common.event.project.ProjectDeletedEvent;
import com.aptech.common.event.project.ProjectUpdatedEvent;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements IProjectService {

        private final ProjectRepository repo;
        private final ProjectMapper mapper;
        private final KafkaProducerService kafkaProducerService;

        @Override
        public ProjectResponse create(ProjectRequest request, String createdBy) {
                String id = UUID.randomUUID().toString();
                repo.createProject(id, request.getName(), request.getDescription(), createdBy, request.getStartDate(),
                                request.getEndDate(), request.getStatusId());

                Project project = repo.findById(id)
                                .orElseThrow(() -> new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION));

                ProjectCreatedEvent event = new ProjectCreatedEvent(
                                id,
                                request.getName(),
                                request.getDescription(),
                                createdBy,
                                request.getStartDate(),
                                request.getEndDate(),
                                request.getStatusId());

                kafkaProducerService.send("user-events", event);

                return mapper.toDto(project);
        }

        @Override
        public List<ProjectResponse> getAllByUser(String userId) {
                return repo.findProjectsByUser(userId).stream()
                                .map(mapper::toDto)
                                .toList();
        }

        @Override
        public ProjectResponse getById(String id) {
                return repo.findById(id).map(mapper::toDto)
                                .orElseThrow(() -> new AppException(ErrorCode.UNAUTHORIZED));
        }

        @Override
        public void update(String id, ProjectUpdateRequest request) {
                repo.updateProject(id, request.getName(), request.getDescription(), request.getStartDate(),
                                request.getEndDate(), request.getStatusId(), request.getUpdatedBy());
                ProjectUpdatedEvent event = new ProjectUpdatedEvent(
                                id,
                                request.getName(),
                                request.getDescription(),
                                request.getStartDate(),
                                request.getEndDate(),
                                request.getStatusId(),
                                request.getUpdatedBy());

                kafkaProducerService.send("user-events", event);
        }

        @Override
        public void delete(String id) {
                repo.deleteProject(id);
                ProjectDeletedEvent event = new ProjectDeletedEvent(id);
                kafkaProducerService.send("user-events", event);
        }
}
