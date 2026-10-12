package com.project.dine.reserve.config.annotation;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RedissonLock {
    String value();
    long waitTime() default 5000L; // 5초
    long leaseTime() default 30000L; // 2초
}
