package com.Aptech.bugtrackingservice.Services.Interfaces;

import com.aptech.common.event.project.ProjectCreatedEvent;

public interface ProjectLookupService {
    public void save(ProjectCreatedEvent event);
}
