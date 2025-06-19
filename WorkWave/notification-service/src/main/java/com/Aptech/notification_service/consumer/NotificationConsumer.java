package com.Aptech.notification_service.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.Aptech.notification_service.config.SendGridEmailService;
import com.aptech.common.event.email.NotificationEvent;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class NotificationConsumer {

    private final SendGridEmailService emailService;

    @KafkaListener(topics = "notification-events", groupId = "notification-group", containerFactory = "kafkaListenerContainerFactory")
    public void listen(NotificationEvent event) {
        log.info("Received NotificationEvent: {}", event);

        try {
            emailService.sendTemplateEmail(
                    event.getRecipientEmail(),
                    event.getTemplateId(),
                    event.getDynamicData());
            log.info("Email sent to {}", event.getRecipientEmail());
        } catch (Exception e) {
            log.error("Failed to send email: {}", e.getMessage(), e);
        }
    }
}
