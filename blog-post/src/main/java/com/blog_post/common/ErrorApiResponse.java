package com.blog_post.common;

import lombok.*;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorApiResponse {

    private Instant timestamp;

    private Integer status;

    private String error;

    private String message;

    private String traceId;
}