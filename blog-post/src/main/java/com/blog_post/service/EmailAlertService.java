package com.blog_post.service;

import com.blog_post.health.HealthStatusDto;

public interface EmailAlertService {

void sendAlert(
    HealthStatusDto status
);
}
