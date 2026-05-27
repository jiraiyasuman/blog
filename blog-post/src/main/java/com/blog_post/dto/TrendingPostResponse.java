package com.blog_post.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrendingPostResponse {

    private Long postId;

    private String title;

    private Long likeCount;

    private Long commentCount;

    private Double trendingScore;
}