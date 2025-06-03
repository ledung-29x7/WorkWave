package com.Aptech.bugtrackingservice.Services.Interfaces;

import com.aptech.common.event.user.UserCreatedEvent;
import com.aptech.common.event.user.UserUpdatedEvent;

public interface UserLookupService {
    public void save(UserCreatedEvent event);

    public void update(UserUpdatedEvent event);

    public void delete(String userId);
}
