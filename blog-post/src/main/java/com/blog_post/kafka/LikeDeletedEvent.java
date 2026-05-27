package com.blog_post.kafka;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LikeDeletedEvent {
	private Long likeId;
	private Long postId;
	private Long userId;
}
