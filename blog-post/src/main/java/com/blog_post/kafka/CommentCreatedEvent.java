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
public class CommentCreatedEvent {

	private Long commentId;
	private Long postid;
	private Long userId;
	private String comment;
	private LocalDateTime createdAt;
}
