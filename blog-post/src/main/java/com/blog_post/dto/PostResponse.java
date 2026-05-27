package com.blog_post.dto;

import lombok.*;

import java.time.Instant;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostResponse {

    private Long postId;

    private Long userId;

    private String title;

    private String content;

    private Long likeCount;

    private Long commentCount;

    private LocalDateTime createdAt;
}
