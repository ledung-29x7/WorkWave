package com.Aptech.projectservice.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.Aptech.projectservice.Entitys.UserLookup;
import com.Aptech.projectservice.Repositorys.UserLookupRepository;
import com.aptech.common.event.user.UserCreatedEvent;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserEventListener {

    private final UserLookupRepository userLookupRepository;

    @KafkaListener(topics = "user-events", groupId = "${spring.application.name}")
    public void handleUserCreated(UserCreatedEvent event) {
        log.info("✅ [PROJECT] Received USER_CREATED: {}", event);

        if (userLookupRepository.ExistsByUserId(event.getUserId()) == 1)
            return;

        // Lưu vào bảng phụ
        UserLookup lookup = new UserLookup();
        lookup.setUserId(event.getUserId());
        lookup.setUserName(event.getUserName());
        lookup.setEmail(event.getEmail());

        // userLookupRepository.save(lookup);
        userLookupRepository.CreateUserLockup(lookup.getUserId(), lookup.getUserName(), lookup.getEmail());
        log.info("✅ [PROJECT] Saved to UserLookup: {}", lookup);
    }

}
