package com.blog_post.service.impl;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.blog_post.health.AlertEventDto;
import com.blog_post.health.HealthConstants;
import com.blog_post.service.KafkaAlertService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaAlertServiceImpl
        implements KafkaAlertService {

    private final KafkaTemplate<String, Object>
            kafkaTemplate;

    @Override
    public void publishAlert(
            AlertEventDto event
    ) {

        kafkaTemplate.send(

                HealthConstants
                        .HEALTH_ALERT_TOPIC,

                event
        );

        log.error(
                "Kafka Health Alert Published: {}",
                event.getServiceName()
        );
    }
}
