package com.Aptech.notification_service.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
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
    public void handleUserEvents(ConsumerRecord<String, Object> record) {
        Object payload = record.value();

        if (payload instanceof UserCreatedEvent createdEvent) {
            messagingTemplate.convertAndSend("/topic/user-created", createdEvent);
            System.out.println("📤 Sent WebSocket: user-created");

        } else if (payload instanceof UserUpdatedEvent updatedEvent) {
            messagingTemplate.convertAndSend("/topic/user-updated", updatedEvent);
            System.out.println("📤 Sent WebSocket: user-updated");

        } else if (payload instanceof UserDeletedEvent deletedEvent) {
            messagingTemplate.convertAndSend("/topic/user-deleted", deletedEvent);
            System.out.println("📤 Sent WebSocket: user-deleted");

        } else {
            System.err.println("⚠️ Unknown user event type: " + payload.getClass().getName());
        }
    }
}
