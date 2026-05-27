package com.blog_post.health;

import lombok.*;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AlertEventDto {

    private String serviceName;

    private String status;

    private String message;

    private Instant timestamp;
}
