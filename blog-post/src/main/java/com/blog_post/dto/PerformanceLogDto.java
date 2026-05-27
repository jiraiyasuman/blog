package com.blog_post.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PerformanceLogDto {

    private String className;

    private String methodName;

    private Long executionTimeMs;
}