package com.Aptech.userservice.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import com.Aptech.userservice.Services.Interfaces.ProjectLookupService;
import com.aptech.common.event.project.ProjectCreatedEvent;
import com.aptech.common.event.project.ProjectDeletedEvent;
import com.aptech.common.event.project.ProjectUpdatedEvent;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProjectEventListener {

    private final ProjectLookupService projectLookupService;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "project-events", groupId = "${spring.application.name}")
    public void handle(Map<String, Object> payload,
            @Header("eventType") String eventType,
            Acknowledgment ack) {
        try {
            log.info("📩 [USER SERVICE] Received eventType: {}", eventType);

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
            ack.acknowledge(); // 👈 Xác nhận thủ công kể cả khi lỗi
        }
    }
}
