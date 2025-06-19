package com.Aptech.notification_service.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import com.aptech.common.event.project.TaskCreatedEvent;
import com.aptech.common.event.project.TaskDeletedEvent;
import com.aptech.common.event.project.TaskUpdatedEvent;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TaskEventListener {

    private final SimpMessagingTemplate messagingTemplate;

    @KafkaListener(topics = "task-events", groupId = "notification-group")
    public void handleTaskEvents(ConsumerRecord<String, Object> record) {
        Object payload = record.value();

        if (payload instanceof TaskCreatedEvent createdEvent) {
            messagingTemplate.convertAndSend("/topic/task-created", createdEvent);
            System.out.println("📤 Sent WebSocket: task-created");

        } else if (payload instanceof TaskUpdatedEvent updatedEvent) {
            messagingTemplate.convertAndSend("/topic/task-updated", updatedEvent);
            System.out.println("📤 Sent WebSocket: task-updated");

        } else if (payload instanceof TaskDeletedEvent deletedEvent) {
            messagingTemplate.convertAndSend("/topic/task-deleted", deletedEvent);
            System.out.println("📤 Sent WebSocket: task-deleted");

        } else {
            System.err.println("⚠️ Unknown task event type: " + payload.getClass().getName());
        }
    }
}
