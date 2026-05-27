package com.blog_post.dto;

import lombok.*;

import java.time.Instant;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostSummaryResponse {

    private Long postId;

    private String title;

    private Long likeCount;

    private Long commentCount;

    private LocalDateTime createdAt;
}