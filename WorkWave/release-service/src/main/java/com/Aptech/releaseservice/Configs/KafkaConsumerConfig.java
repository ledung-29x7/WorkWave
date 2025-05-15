package com.Aptech.releaseservice.Configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.ConsumerAwareRecordRecoverer;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.util.backoff.ExponentialBackOff;

@Configuration
public class KafkaConsumerConfig {

    @Bean
    public DefaultErrorHandler errorHandler(KafkaTemplate<Object, Object> kafkaTemplate) {
        // ✅ Kafka 3.x: dùng ConsumerAwareRecordRecoverer
        ConsumerAwareRecordRecoverer recoverer = new DeadLetterPublishingRecoverer(kafkaTemplate);

        // Retry tối đa 3 lần, delay tăng dần: 1s → 2s → 4s
        ExponentialBackOff backOff = new ExponentialBackOff(1000L, 2.0); // initial interval + multiplier
        backOff.setMaxElapsedTime(0); // Retry vô hạn số lần cho mỗi record (có thể set nếu muốn)

        return new DefaultErrorHandler(recoverer, backOff);
    }
}