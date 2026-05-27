package com.blog_post.kafka;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SagaEvent {

	private String sagaId;
	private String eventType;
	private String status;
	private Long postId;
	private Long userId;
	private LocalDateTime createdAt;
}
