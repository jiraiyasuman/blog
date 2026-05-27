package com.blog_post.exception;

import java.time.Instant;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ValidationErrorResponse {

	private Instant timestamp;

    private Integer status;

    private String message;

    private Map<String, String> fieldErrors;

    private String path;
}
