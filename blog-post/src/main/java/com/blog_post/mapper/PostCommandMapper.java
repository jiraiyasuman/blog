package com.blog_post.mapper;

import com.blog_post.dto.PostCommandResponse;
import com.blog_post.entity.PostWriteEntity;

public class PostCommandMapper {

private PostCommandMapper() {}

public static PostCommandResponse
mapToResponse(
    PostWriteEntity entity
) {

return PostCommandResponse.builder()

        .postId(entity.getPostId())

        .userId(entity.getUserId())

        .title(entity.getTitle())

        .content(entity.getContent())

        .createdAt(entity.getCreatedOn())

        .build();
}
}