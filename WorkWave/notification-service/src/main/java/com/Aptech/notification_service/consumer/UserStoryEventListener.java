package com.Aptech.notification_service.consumer;

import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.header.Header;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import com.aptech.common.event.project.UserStoryCreatedEvent;
import com.aptech.common.event.project.UserStoryDeletedEvent;
import com.aptech.common.event.project.UserStoryUpdatedEvent;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserStoryEventListener {

    private final SimpMessagingTemplate messagingTemplate;

    @KafkaListener(topics = "userstory-events", groupId = "notification-group")
    public void handleUserStoryEvents(ConsumerRecord<String, Object> record) {
        Object payload = record.value();

        if (payload instanceof UserStoryCreatedEvent createdEvent) {
            messagingTemplate.convertAndSend("/topic/userstory-created", createdEvent);

        } else if (payload instanceof UserStoryUpdatedEvent updatedEvent) {
            messagingTemplate.convertAndSend("/topic/userstory-updated", updatedEvent);

        } else if (payload instanceof UserStoryDeletedEvent deletedEvent) {
            messagingTemplate.convertAndSend("/topic/userstory-deleted", deletedEvent);

        } else {
            System.out.println("⚠️ Unknown event type: " + payload.getClass().getName());
        }
    }

}
