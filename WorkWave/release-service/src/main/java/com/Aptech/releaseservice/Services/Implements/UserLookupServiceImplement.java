package com.Aptech.releaseservice.Services.Implements;

import org.springframework.stereotype.Service;

import com.Aptech.releaseservice.Repositorys.UserLookupRepository;
import com.Aptech.releaseservice.Services.Interfaces.UserLookupService;
import com.aptech.common.event.user.UserCreatedEvent;
import com.aptech.common.event.user.UserUpdatedEvent;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserLookupServiceImplement implements UserLookupService {
    private final UserLookupRepository userLookupRepository;

    @Override
    public void save(UserCreatedEvent event) {
        if (userLookupRepository.ExistsByUserId(event.getUserId()) == 1)
            return;
        userLookupRepository.CreateUserLockup(event.getUserId(), event.getUserName(), event.getEmail());
    }

    @Override
    public void delete(String userId) {
        userLookupRepository.deleteUserLookup(userId);

    }

    @Override
    public void update(UserUpdatedEvent event) {
        userLookupRepository.updateUserByProcedure(event.getUserId(), event.getUserName(), event.getEmail());
    }
}
