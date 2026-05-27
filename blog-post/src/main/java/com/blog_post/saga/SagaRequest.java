package com.blog_post.saga;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SagaRequest {

	private String sagaId;
	private Long postId;
	private Long userId;
	private String eventType;
	private Object payload;
	
}
