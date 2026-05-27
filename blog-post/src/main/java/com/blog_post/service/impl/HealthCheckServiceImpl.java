package com.blog_post.service.impl;

import java.time.Instant;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.blog_post.health.AlertEventDto;
import com.blog_post.health.HealthClient;
import com.blog_post.health.HealthStatusDto;
import com.blog_post.health.ServiceStatus;
import com.blog_post.service.EmailAlertService;
import com.blog_post.service.HealthCheckService;
import com.blog_post.service.KafkaAlertService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class HealthCheckServiceImpl
        implements HealthCheckService {

    private final HealthClient
            healthClient;

    private final EmailAlertService
            emailAlertService;

    private final KafkaAlertService
            kafkaAlertService;

    @Override
    public void performHealthChecks() {

        List<HealthStatusDto> services =
                List.of(

                        build(
                                "POST-SERVICE",
                                "http://localhost:8082/actuator/health"
                        ),

                        build(
                                "API-GATEWAY",
                                "http://localhost:8080/actuator/health"
                        ),

                        build(
                                "EUREKA-SERVER",
                                "http://localhost:8761/actuator/health"
                        ),

                        build(
                                "CONFIG-SERVER",
                                "http://localhost:8888/actuator/health"
                        ),

                        build(
                                "LOGIN-SERVICE",
                                "http://localhost:8081/actuator/health"
                        ),

                        build(
                                "LIKE-SERVICE",
                                "http://localhost:8083/actuator/health"
                        ),

                        build(
                                "COMMENT-SERVICE",
                                "http://localhost:8084/actuator/health"
                        )
                );

        services.forEach(this::checkService);
    }

    private void checkService(
            HealthStatusDto service
    ) {

        try {

            ResponseEntity<String> response =
                    healthClient.checkHealth(
                            service.getUrl()
                    );

            if (response.getStatusCode()
                    .is2xxSuccessful()) {

                log.info(
                        "{} is UP",
                        service.getServiceName()
                );

            } else {

                triggerAlerts(service);
            }

        } catch (Exception ex) {

            log.error(
                    "{} is DOWN",
                    service.getServiceName(),
                    ex
            );

            service.setStatus(
                    ServiceStatus.DOWN
            );

            service.setMessage(
                    ex.getMessage()
            );

            triggerAlerts(service);
        }
    }

    private void triggerAlerts(
            HealthStatusDto service
    ) {

        emailAlertService
                .sendAlert(service);

        kafkaAlertService
                .publishAlert(

                        AlertEventDto.builder()

                                .serviceName(
                                        service.getServiceName()
                                )

                                .status(
                                        service.getStatus()
                                                .name()
                                )

                                .message(
                                        service.getMessage()
                                )

                                .timestamp(
                                        Instant.now()
                                )

                                .build()
                );
    }

    private HealthStatusDto build(

            String name,

            String url

    ) {

        return HealthStatusDto.builder()

                .serviceName(name)

                .url(url)

                .status(ServiceStatus.UP)

                .checkedAt(Instant.now())

                .build();
    }
}