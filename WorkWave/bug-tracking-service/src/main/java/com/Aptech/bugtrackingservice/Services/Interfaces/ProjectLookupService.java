package com.Aptech.bugtrackingservice.Services.Interfaces;

import com.aptech.common.event.project.ProjectCreatedEvent;
import com.aptech.common.event.project.ProjectUpdatedEvent;

public interface ProjectLookupService {
    public void save(ProjectCreatedEvent event);

    public void deleteProjectLookup(String projectId);

    public void update(ProjectUpdatedEvent event);
}
