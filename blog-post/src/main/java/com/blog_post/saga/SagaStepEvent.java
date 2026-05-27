package com.blog_post.saga;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SagaStepEvent {

	private String sagaId;
	private Long postId;
	private SagaStep step;
	private String serviceName;
	private String message;
	private Instant timeStamp;
}
