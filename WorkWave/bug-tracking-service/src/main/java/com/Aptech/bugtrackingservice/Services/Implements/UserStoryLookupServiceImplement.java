package com.Aptech.bugtrackingservice.Services.Implements;

import org.springframework.stereotype.Service;

import com.Aptech.bugtrackingservice.Repositorys.UserStoryLookupRepository;
import com.Aptech.bugtrackingservice.Services.Interfaces.UserStoryLookupService;
import com.aptech.common.event.project.UserStoryCreatedEvent;
import com.aptech.common.event.project.UserStoryUpdatedEvent;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserStoryLookupServiceImplement implements UserStoryLookupService {
    private final UserStoryLookupRepository repository;

    @Override
    public void save(UserStoryCreatedEvent event) {
        if (repository.ExistsByUserStoryLookupId(event.getStoryId()) == 1)
            return;

        repository.saveUserStoryLookup(
                event.getStoryId(),
                event.getName(),
                event.getDescription(),
                event.getEpicId(),
                event.getPriorityId(),
                event.getAssignedTo(),
                event.getStatusId(),
                event.getCreatedBy(),
                event.getProjectId());
    }

    @Override

    public void update(UserStoryUpdatedEvent event) {
        repository.updateUserStoryLookup(
                event.getStoryId(),
                event.getName(),
                event.getDescription(),
                event.getEpicId(),
                event.getPriorityId(),
                event.getAssignedTo(),
                event.getStatusId());
    }

    @Override

    public void delete(Integer storyId) {
        repository.deleteUserStoryLookup(storyId);
    }

}
