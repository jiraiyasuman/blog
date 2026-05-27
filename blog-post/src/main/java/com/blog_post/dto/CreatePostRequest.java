package com.blog_post.dto;

import jakarta.validation.constraints.NotBlank;

import jakarta.validation.constraints.Size;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreatePostRequest {

    @NotBlank(message = "Title is required")
    @Size(max = 255)
    private String title;

    @NotBlank(message = "Content is required")
    private String content;
}
