package com.Aptech.bugtrackingservice.Services.Interfaces;

import com.aptech.common.event.project.TaskCreatedEvent;
import com.aptech.common.event.project.TaskUpdatedEvent;

public interface TaskLookupService {
    void save(TaskCreatedEvent event);

    void update(TaskUpdatedEvent event);

    void delete(Integer taskId);
}
