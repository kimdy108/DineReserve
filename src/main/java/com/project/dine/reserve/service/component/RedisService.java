package com.project.dine.reserve.service.component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Slf4j
@Component
@RequiredArgsConstructor
public class RedisService {
    private final RedisTemplate<String, Object> redisTemplate;

    public void setValues(String key, String data) {
        Duration duration = Duration.ofDays(7);
        ValueOperations<String, Object> values = redisTemplate.opsForValue();
        values.set(key, data, duration);
    }

    public String getValues(String key) {
        try {
            ValueOperations<String, Object> values = redisTemplate.opsForValue();
            return (String) values.get(key);
        } catch (Exception e) {
            return null;
        }
    }

    public void deleteValues(String key) {
        redisTemplate.delete(key);
    }
}
