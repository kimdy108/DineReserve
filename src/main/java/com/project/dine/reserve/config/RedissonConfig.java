package com.project.dine.reserve.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Optional;

@Configuration
public class RedissonConfig {
    @Value("${spring.data.redis.host}")
    private String host;

    @Value("${spring.data.redis.port}")
    private int port;

    @Value("${spring.data.redis.password}")
    private String password;

    @Bean
    public RedissonClient redissonClient() {
        String redisAddress = "redis://%s:%d".formatted(host, port);

        Config config = new Config();
        SingleServerConfig serverConfig = config.useSingleServer().setAddress(redisAddress);

        // 비밀번호가 존재하면 설정
        Optional.ofNullable(password)
                .filter(pwd -> !pwd.isBlank())
                .ifPresent(serverConfig::setPassword);

        return Redisson.create(config);
    }
}
