package com.Aptech.testservice.kafka;

import com.Aptech.testservice.Services.Interfaces.UserStoryLookupService;
import com.aptech.common.event.project.UserStoryCreatedEvent;
import com.aptech.common.event.project.UserStoryDeletedEvent;
import com.aptech.common.event.project.UserStoryUpdatedEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserStoryEventListener {

    private final ObjectMapper objectMapper;
    private final UserStoryLookupService userStoryLookupService;

    @KafkaListener(topics = "userstory-events", groupId = "${spring.application.name}")
    public void handle(Map<String, Object> payload,
            @Header("eventType") String eventType,
            Acknowledgment ack) {
        try {
            log.info("📨 [USERSTORY EVENT] Received eventType: {}", eventType);

            switch (eventType) {
                case "UserStoryCreatedEvent" -> {
                    UserStoryCreatedEvent event = objectMapper.convertValue(payload, UserStoryCreatedEvent.class);
                    userStoryLookupService.save(event);
                }

                case "UserStoryDeletedEvent" -> {
                    UserStoryDeletedEvent event = objectMapper.convertValue(payload, UserStoryDeletedEvent.class);
                    userStoryLookupService.delete(event.getStoryId());
                }

                case "UserStoryUpdatedEvent" -> {
                    UserStoryUpdatedEvent event = objectMapper.convertValue(payload, UserStoryUpdatedEvent.class);
                    userStoryLookupService.update(event);
                }

                default -> log.warn("⚠️ Unknown UserStory eventType: {}", eventType);
            }

        } catch (Exception e) {
            log.error("❌ Error handling UserStory event [{}]: {}", eventType, e.getMessage(), e);
        } finally {
            ack.acknowledge(); // luôn luôn gọi ack
        }
    }
}