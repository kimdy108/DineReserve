package com.project.dine.reserve.service.component;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
@Slf4j
public class LoggerService {
    public void writeLogger(String type, String message) {
        String nowDate = "[" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + "] ";
        switch (type.toUpperCase()) {
            case "TRACE" -> log.trace("{}{}", nowDate, message);
            case "DEBUG" -> log.debug("{}{}", nowDate, message);
            case "INFO" -> log.info("{}{}", nowDate, message);
            case "WARN" -> log.warn("{}{}", nowDate, message);
            case "ERROR" -> log.error("{}{}", nowDate, message);
            default -> log.error("NO TYPE : {}{}", nowDate, message);
        }
    }
}
