package com.Aptech.notification_service.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import com.aptech.common.event.project.ProjectCreatedEvent;
import com.aptech.common.event.project.ProjectDeletedEvent;
import com.aptech.common.event.project.ProjectUpdatedEvent;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ProjectEventListener {

    private final SimpMessagingTemplate messagingTemplate;

    @KafkaListener(topics = "project-events", groupId = "notification-group")
    public void handleProjectEvents(ConsumerRecord<String, Object> record) {
        Object payload = record.value();

        if (payload instanceof ProjectCreatedEvent createdEvent) {
            messagingTemplate.convertAndSend("/topic/project-created", createdEvent);
            System.out.println("📤 Sent WebSocket: project-created");

        } else if (payload instanceof ProjectUpdatedEvent updatedEvent) {
            messagingTemplate.convertAndSend("/topic/project-updated", updatedEvent);
            System.out.println("📤 Sent WebSocket: project-updated");

        } else if (payload instanceof ProjectDeletedEvent deletedEvent) {
            messagingTemplate.convertAndSend("/topic/project-deleted", deletedEvent);
            System.out.println("📤 Sent WebSocket: project-deleted");

        } else {
            System.err.println("⚠️ Unknown project event type: " + payload.getClass().getName());
        }
    }
}
