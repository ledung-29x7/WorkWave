package com.Aptech.notification_service.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import com.aptech.common.event.user.UserCreatedEvent;
import com.aptech.common.event.user.UserDeletedEvent;
import com.aptech.common.event.user.UserUpdatedEvent;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserEventListener {

    private final SimpMessagingTemplate messagingTemplate;

    @KafkaListener(topics = "user-events", groupId = "notification-group")
    public void handleCreated(UserCreatedEvent event) {
        messagingTemplate.convertAndSend("/topic/user-created", event);
    }

    @KafkaListener(topics = "user-events", groupId = "notification-group")
    public void handleUpdated(UserUpdatedEvent event) {
        messagingTemplate.convertAndSend("/topic/user-updated", event);
    }

    @KafkaListener(topics = "user-events", groupId = "notification-group")
    public void handleDeleted(UserDeletedEvent event) {
        messagingTemplate.convertAndSend("/topic/user-deleted", event);
    }
}
