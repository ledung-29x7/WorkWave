package com.Aptech.bugtrackingservice.Services.Implements;

import org.springframework.stereotype.Service;

import com.Aptech.bugtrackingservice.Repositorys.ProjectLookupRepository;
import com.Aptech.bugtrackingservice.Services.Interfaces.ProjectLookupService;
import com.aptech.common.event.project.ProjectCreatedEvent;
import com.aptech.common.event.project.ProjectUpdatedEvent;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProjectLookupServiceImplement implements ProjectLookupService {

    private final ProjectLookupRepository projectLookupRepository;

    @Override
    public void save(ProjectCreatedEvent event) {
        if (projectLookupRepository.ExistsByProjectId(event.getProjectId()) == 1)
            return;

        projectLookupRepository.saveProjectLookup(
                event.getProjectId(),
                event.getName(),
                event.getDescription(),
                event.getCreatedBy(),
                event.getStartDate(),
                event.getEndDate(),
                event.getStatusId());
    }

    @Override
    public void deleteProjectLookup(String projectId) {
        projectLookupRepository.deleteProjectLookup(projectId);
    }

    @Override
    public void update(ProjectUpdatedEvent event) {
        projectLookupRepository.updateProjectLookup(
                event.getProjectId(),
                event.getName(),
                event.getDescription(),
                event.getStartDate(),
                event.getEndDate(),
                event.getStatusId(),
                event.getUpdatedBy());
    }

}
