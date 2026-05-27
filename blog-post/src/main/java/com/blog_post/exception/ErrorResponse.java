package com.blog_post.exception;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {
 
	private Instant timestamp;
	private Integer status;
	private ErrorCode errorCode;
	private String message;
	private String path;
	private String traceId;
}
