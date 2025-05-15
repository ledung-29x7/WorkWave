package com.Aptech.testservice.Services.Interfaces;

import com.aptech.common.event.project.UserStoryCreatedEvent;
import com.aptech.common.event.project.UserStoryUpdatedEvent;

public interface UserStoryLookupService {
    public void save(UserStoryCreatedEvent event);

    public void update(UserStoryUpdatedEvent event);

    public void delete(Integer storyId);
}
