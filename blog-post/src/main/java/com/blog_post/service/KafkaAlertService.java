package com.blog_post.service;

import com.blog_post.health.AlertEventDto;

public interface KafkaAlertService {

    void publishAlert(
            AlertEventDto event
    );
}
