package com.blog_post.config;

import io.github.resilience4j.circuitbreaker
.CircuitBreakerConfig;

import io.github.resilience4j.timelimiter
.TimeLimiterConfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class Resilience4jConfig {

@Bean
public CircuitBreakerConfig circuitBreakerConfig() {

return CircuitBreakerConfig.custom()
        .failureRateThreshold(50)
        .waitDurationInOpenState(
                Duration.ofSeconds(10)
        )
        .slidingWindowSize(10)
        .permittedNumberOfCallsInHalfOpenState(3)
        .build();
}

@Bean
public TimeLimiterConfig timeLimiterConfig() {

return TimeLimiterConfig.custom()
        .timeoutDuration(Duration.ofSeconds(3))
        .build();
}
}