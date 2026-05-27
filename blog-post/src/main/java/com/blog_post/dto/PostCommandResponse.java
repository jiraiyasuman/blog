package com.blog_post.dto;

import lombok.*;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostCommandResponse {

    private Long postId;

    private Long userId;

    private String title;

    private String content;

    private Instant createdAt;
}