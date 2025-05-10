package com.Aptech.testservice.Services;

import com.aptech.common.event.project.ProjectCreatedEvent;

public interface ProjectLookupService {
    public void save(ProjectCreatedEvent event);

    public void deleteProjectLookup(String projectId);
}
