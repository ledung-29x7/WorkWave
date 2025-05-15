package com.Aptech.releaseservice.kafka;

import java.util.Map;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import com.Aptech.releaseservice.Services.Interfaces.UserLookupService;
import com.aptech.common.event.user.UserCreatedEvent;
import com.aptech.common.event.user.UserDeletedEvent;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserEventListener {

    private final ObjectMapper objectMapper;
    private final UserLookupService userLookupService;

    @KafkaListener(topics = "user-events", groupId = "${spring.application.name}")
    public void handle(Map<String, Object> payload,
            @Header("eventType") String eventType,
            Acknowledgment ack) {
        try {
            log.info("📨 [USER EVENT] Received eventType: {}", eventType);

            switch (eventType) {
                case "UserCreatedEvent" -> {
                    UserCreatedEvent event = objectMapper.convertValue(payload, UserCreatedEvent.class);
                    userLookupService.save(event);
                }
                case "UserDeletedEvent" -> {
                    UserDeletedEvent event = objectMapper.convertValue(payload, UserDeletedEvent.class);
                    userLookupService.delete(event.getUserId());
                }
                default -> log.warn("⚠️ Unknown eventType: {}", eventType);
            }

        } catch (Exception e) {
            log.error("❌ Error handling user event: {}", eventType, e);
        } finally {
            ack.acknowledge();
        }
    }
}
