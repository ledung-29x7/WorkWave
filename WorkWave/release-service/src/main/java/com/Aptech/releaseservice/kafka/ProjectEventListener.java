package com.Aptech.releaseservice.kafka;

import com.Aptech.releaseservice.Services.ProjectLookupService;
import com.aptech.common.event.project.ProjectCreatedEvent;
import com.aptech.common.event.project.ProjectDeletedEvent;
import com.aptech.common.event.project.ProjectUpdatedEvent;
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
public class ProjectEventListener {

    private final ProjectLookupService projectLookupService;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "project-events", groupId = "${spring.application.name}")
    public void handle(Map<String, Object> payload,
            @Header("eventType") String eventType,
            Acknowledgment ack) {
        try {
            log.info("📩 [{}] Received eventType: {}", "${spring.application.name}", eventType);

            switch (eventType) {
                case "ProjectCreatedEvent" -> {
                    ProjectCreatedEvent event = objectMapper.convertValue(payload, ProjectCreatedEvent.class);
                    projectLookupService.save(event);
                }
                case "ProjectDeletedEvent" -> {
                    ProjectDeletedEvent event = objectMapper.convertValue(payload, ProjectDeletedEvent.class);
                    projectLookupService.deleteProjectLookup(event.getProjectId());
                }
                case "ProjectUpdatedEvent" -> {
                    ProjectUpdatedEvent event = objectMapper.convertValue(payload, ProjectUpdatedEvent.class);
                    projectLookupService.update(event);
                }

                default -> log.warn("⚠️ Unknown eventType: {}", eventType);
            }

        } catch (Exception ex) {
            log.error("❌ Error handling event {}: {}", eventType, ex.getMessage(), ex);
        } finally {
            ack.acknowledge();
        }
    }
}
