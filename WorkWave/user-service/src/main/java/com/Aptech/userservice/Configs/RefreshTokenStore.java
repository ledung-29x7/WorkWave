package com.Aptech.userservice.Configs;

import java.time.Duration;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RefreshTokenStore {

    private final StringRedisTemplate redis;

    public void save(String userId, String refreshToken) {
        redis.opsForValue().set("refresh:" + userId, refreshToken, Duration.ofDays(7));
    }

    public String get(String userId) {
        return redis.opsForValue().get("refresh:" + userId);
    }

    public void remove(String userId) {
        redis.delete("refresh:" + userId);
    }
}
