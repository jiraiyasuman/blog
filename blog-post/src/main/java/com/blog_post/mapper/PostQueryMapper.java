package com.blog_post.mapper;

import com.blog_post.dto.PostResponse;
import com.blog_post.dto.PostSummaryResponse;
import com.blog_post.entity.PostReadEntity;

public class PostQueryMapper {

    private PostQueryMapper() {}

    public static PostResponse mapToResponse(
            PostReadEntity entity
    ) {

        return PostResponse.builder()

                .postId(entity.getPostId())

                .userId(entity.getUserId())

                .title(entity.getTitle())

                .content(entity.getContent())

                .likeCount(entity.getLikeCount())

                .commentCount(
                        entity.getCommentCount()
                )

                .createdAt(entity.getCreatedAt())

                .build();
    }

    public static PostSummaryResponse
    mapToSummary(
            PostReadEntity entity
    ) {

        return PostSummaryResponse.builder()

                .postId(entity.getPostId())

                .title(entity.getTitle())

                .likeCount(entity.getLikeCount())

                .commentCount(
                        entity.getCommentCount()
                )

                .createdAt(entity.getCreatedAt())

                .build();
    }
}