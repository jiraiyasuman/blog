package com.blog_post.kafka;

import java.time.LocalDateTime;

import groovy.transform.builder.Builder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostUpdatedEvent {
	private Long postId;
	
	private String title;
	
	private String content;
	
	private LocalDateTime updatedAt;
}