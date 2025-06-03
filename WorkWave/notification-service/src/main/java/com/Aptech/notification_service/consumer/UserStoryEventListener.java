package com.Aptech.notification_service.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import com.aptech.common.event.project.UserStoryCreatedEvent;
import com.aptech.common.event.project.UserStoryDeletedEvent;
import com.aptech.common.event.project.UserStoryUpdatedEvent;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserStoryEventListener {

    private final SimpMessagingTemplate messagingTemplate;

    @KafkaListener(topics = "userstory-events", groupId = "notification-group")
    public void handleCreated(UserStoryCreatedEvent event) {
        messagingTemplate.convertAndSend("/topic/userstory-created", event);
    }

    @KafkaListener(topics = "userstory-events", groupId = "notification-group")
    public void handleUpdated(UserStoryUpdatedEvent event) {
        messagingTemplate.convertAndSend("/topic/userstory-updated", event);
    }

    @KafkaListener(topics = "userstory-events", groupId = "notification-group")
    public void handleDeleted(UserStoryDeletedEvent event) {
        messagingTemplate.convertAndSend("/topic/userstory-deleted", event);
    }
}
