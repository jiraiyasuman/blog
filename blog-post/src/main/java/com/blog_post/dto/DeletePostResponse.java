package com.blog_post.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeletePostResponse {

    private Long postId;

    private Boolean deleted;
}
