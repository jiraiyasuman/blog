package com.blog_post.resilience;

import java.time.Duration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.retry.RetryConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;

@Configuration
public class ResilienceCustomizer {

    @Bean
    public CircuitBreakerConfig
    defaultCircuitBreakerConfig() {

        return CircuitBreakerConfig.custom()

                .failureRateThreshold(50)

                .minimumNumberOfCalls(5)

                .slidingWindowSize(10)

                .waitDurationInOpenState(
                        Duration.ofSeconds(10)
                )

                .permittedNumberOfCallsInHalfOpenState(3)

                .build();
    }

    @Bean
    public RetryConfig retryConfig() {

        return RetryConfig.custom()

                .maxAttempts(3)

                .waitDuration(
                        Duration.ofSeconds(2)
                )

                .build();
    }

    @Bean
    public TimeLimiterConfig
    timeLimiterConfig() {

        return TimeLimiterConfig.custom()

                .timeoutDuration(
                        Duration.ofSeconds(3)
                )

                .build();
    }
}