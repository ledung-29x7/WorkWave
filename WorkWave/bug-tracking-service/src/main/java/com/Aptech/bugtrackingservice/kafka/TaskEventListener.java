package com.Aptech.bugtrackingservice.kafka;

import java.util.Map;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import com.Aptech.bugtrackingservice.Services.Interfaces.TaskLookupService;
import com.aptech.common.event.project.TaskCreatedEvent;
import com.aptech.common.event.project.TaskDeletedEvent;
import com.aptech.common.event.project.TaskUpdatedEvent;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class TaskEventListener {

    private final ObjectMapper objectMapper;
    private final TaskLookupService taskLookupService;

    @KafkaListener(topics = "task-events", groupId = "${spring.application.name}")
    public void handle(Map<String, Object> payload,
            @Header("eventType") String eventType,
            Acknowledgment ack) {
        try {
            log.info("📨 [TASK EVENT] Received: {}", eventType);

            switch (eventType) {
                case "TaskCreatedEvent" -> {
                    TaskCreatedEvent event = objectMapper.convertValue(payload, TaskCreatedEvent.class);
                    taskLookupService.save(event);
                }

                case "TaskUpdatedEvent" -> {
                    TaskUpdatedEvent event = objectMapper.convertValue(payload, TaskUpdatedEvent.class);
                    taskLookupService.update(event);
                }

                case "TaskDeletedEvent" -> {
                    TaskDeletedEvent event = objectMapper.convertValue(payload, TaskDeletedEvent.class);
                    taskLookupService.delete(event.getTaskId());
                }

                default -> log.warn("⚠️ Unknown Task eventType: {}", eventType);
            }

        } catch (Exception e) {
            log.error("❌ Error handling Task event [{}]: {}", eventType, e.getMessage(), e);
        } finally {
            ack.acknowledge();
        }
    }
}
