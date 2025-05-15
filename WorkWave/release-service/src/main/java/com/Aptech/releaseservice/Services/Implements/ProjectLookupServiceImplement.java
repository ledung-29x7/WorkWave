package com.Aptech.releaseservice.Services.Implements;

import org.springframework.stereotype.Service;

import com.Aptech.releaseservice.Repositorys.ProjectLookupRepository;
import com.Aptech.releaseservice.Services.ProjectLookupService;
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
                event.getCreatedBy());
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
                event.getDescription());
    }

}
