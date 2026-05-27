package com.blog_post.dto;

import lombok.*;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuditLogDto {

    private String action;

    private Long userId;

    private String method;

    private String className;

    private Instant timestamp;
}