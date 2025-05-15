package com.Aptech.releaseservice.Services.Interfaces;

import com.aptech.common.event.user.UserCreatedEvent;

public interface UserLookupService {
    public void save(UserCreatedEvent event);

    public void delete(String userId);

}
