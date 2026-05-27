package com.blog_post.common;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {

    private Status status;

    private String message;

    private T data;

    private MetadataDto metadata;
}