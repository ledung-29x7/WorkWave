package com.Aptech.userservice.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
public class DltEventListener {

    @KafkaListener(topics = "project-events.DLT", groupId = "${spring.application.name}-dlt")
    public void handleDLT(Map<String, Object> payload) {
        log.warn("🧟 [DLT] Nhận message lỗi: {}", payload);
        // 👉 Tùy bạn: lưu DB, gửi cảnh báo, trigger retry thủ công, v.v.
    }
}
