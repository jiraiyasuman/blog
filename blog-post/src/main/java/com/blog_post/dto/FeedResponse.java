package com.blog_post.dto;

import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FeedResponse {

    private List<PostSummaryResponse> posts;

    private Integer currentPage;

    private Integer totalPages;

    private Long totalElements;

    private Boolean hasNext;
}