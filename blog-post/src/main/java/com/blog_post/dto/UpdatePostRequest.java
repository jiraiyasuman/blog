package com.blog_post.dto;

import jakarta.validation.constraints.NotBlank;

import jakarta.validation.constraints.Size;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdatePostRequest {

    @NotBlank
    @Size(max = 255)
    private String title;

    @NotBlank
    private String content;
}
