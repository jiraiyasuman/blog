package com.blog_post.saga;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SagaResponse {
	private String sagaId;
	private SagaStatus status;
	private String message;
}
