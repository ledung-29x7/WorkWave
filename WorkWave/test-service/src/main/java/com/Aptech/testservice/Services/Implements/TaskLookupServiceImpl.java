package com.Aptech.testservice.Services.Implements;

import org.springframework.stereotype.Service;

import com.Aptech.testservice.Repositorys.TaskLookupRepository;
import com.Aptech.testservice.Services.Interfaces.TaskLookupService;
import com.aptech.common.event.project.TaskCreatedEvent;
import com.aptech.common.event.project.TaskUpdatedEvent;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TaskLookupServiceImpl implements TaskLookupService {

    private final TaskLookupRepository repository;

    @Override
    public void save(TaskCreatedEvent event) {
        if (repository.ExistsByTaskLookupId(event.getTaskId()) == 1)
            return;

        repository.saveTaskLookup(
                event.getTaskId(),
                event.getStoryId(),
                event.getName(),
                event.getDescription(),
                event.getStatusId(),
                event.getEstimatedHours(),
                event.getLoggedHours(),
                event.getRemainingHours(),
                event.getAssignedTo(),
                event.getCreatedBy());
    }

    @Override
    public void update(TaskUpdatedEvent event) {
        repository.updateTaskLookup(
                event.getTaskId(),
                event.getName(),
                event.getDescription(),
                event.getStatusId(),
                event.getEstimatedHours(),
                event.getLoggedHours(),
                event.getRemainingHours(),
                event.getAssignedTo(),
                event.getUpdatedBy());
    }

    @Override
    public void delete(Integer taskId) {
        repository.deleteTaskLookup(taskId);
    }
}
