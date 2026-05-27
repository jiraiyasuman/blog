package com.blog_post.health;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.blog_post.service.HealthCheckService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class HealthCheckScheduler {

    private final HealthCheckService
            healthCheckService;

    @Scheduled(fixedDelay = 30000)
    public void monitorServices() {

        log.info(
                "Running Scheduled Health Checks"
        );

        healthCheckService
                .performHealthChecks();
    }
}
