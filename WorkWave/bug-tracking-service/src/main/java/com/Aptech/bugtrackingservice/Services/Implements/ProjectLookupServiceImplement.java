package com.Aptech.bugtrackingservice.Services.Implements;

import org.springframework.stereotype.Service;

import com.Aptech.bugtrackingservice.Entitys.ProjectLookup;
import com.Aptech.bugtrackingservice.Repositorys.ProjectLookupRepository;
import com.Aptech.bugtrackingservice.Services.ProjectLookupService;
import com.aptech.common.event.project.ProjectCreatedEvent;

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

}
