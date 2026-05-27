package com.blog_post.common;

import lombok.*;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SuccessResponse<T> {

    private Instant timestamp;

    private Integer status;

    private String message;

    private T data;
}