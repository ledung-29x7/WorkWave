package com.Aptech.notification_service.consumer;

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
    public void handleTaskCreated(TaskCreatedEvent event) {
        messagingTemplate.convertAndSend("/topic/task-created", event);
    }

    @KafkaListener(topics = "task-events", groupId = "notification-group")
    public void handleTaskUpdated(TaskUpdatedEvent event) {
        messagingTemplate.convertAndSend("/topic/task-updated", event);
    }

    @KafkaListener(topics = "task-events", groupId = "notification-group")
    public void handleTaskDeleted(TaskDeletedEvent event) {
        messagingTemplate.convertAndSend("/topic/task-deleted", event);
    }
}
