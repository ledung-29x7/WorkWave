package com.Aptech.notification_service.consumer;

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
    public void handleProjectCreated(ProjectCreatedEvent event) {
        messagingTemplate.convertAndSend("/topic/project-created", event);
    }

    @KafkaListener(topics = "project-events", groupId = "notification-group")
    public void handleProjectUpdated(ProjectUpdatedEvent event) {
        messagingTemplate.convertAndSend("/topic/project-updated", event);
    }

    @KafkaListener(topics = "project-events", groupId = "notification-group")
    public void handleProjectDeleted(ProjectDeletedEvent event) {
        messagingTemplate.convertAndSend("/topic/project-deleted", event);
    }
}
