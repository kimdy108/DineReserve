package com.project.dine.reserve.service.component;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.project.dine.reserve.dto.common.RedisLoginSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Slf4j
@Component
@RequiredArgsConstructor
public class RedisService {
    private final Gson gson;
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

    public void setHashValues(String key, String sessionKey, RedisLoginSession redisLoginSession) {
        HashOperations<String, String, String> hashes = redisTemplate.opsForHash();
        hashes.put(key, sessionKey, gson.toJson(redisLoginSession));
    }

    public Map<String, RedisLoginSession> getHashValues(String key) {
        HashOperations<String, String, String> hashes = redisTemplate.opsForHash();
        Map<String, String> values = hashes.entries(key);
        Map<String, RedisLoginSession> sessionMap = new HashMap<>();

        Long nowTimestamp = Timestamp.valueOf(LocalDateTime.now()).getTime();
        Set<String> sessionKeys = values.keySet();
        for (String sessionKey : sessionKeys) {
            String value = values.get(sessionKey);
            RedisLoginSession redisLoginSession = gson.fromJson(value, new TypeToken<RedisLoginSession>() {}.getType());

            if (nowTimestamp > redisLoginSession.getExpireTimestamp()) {
                hashes.delete(key, sessionKey);
                values.remove(sessionKey);
            } else {
                sessionMap.put(sessionKey, redisLoginSession);
            }
        }

        return sessionMap;
    }

    public void deleteHashValues(String key, String sessionKey) {
        HashOperations<String, String, String> hashes = redisTemplate.opsForHash();
        hashes.delete(key, sessionKey);
    }
}
