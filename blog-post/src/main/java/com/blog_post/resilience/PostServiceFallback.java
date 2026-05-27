package com.blog_post.resilience;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class PostServiceFallback {

	
	public LikeResponseDto fallbackLike(
			Long postId,
			Throwable ex
			) {
		log.error("Like Service Fallback Triggered for Post: {}",postId,ex);
		return LikeResponseDto.builder()
				.postId(postId).
				likeCount(0L)
				.liked(false)
				.build();
	}
	
	public CommentResponseDto fallbackComment(
			Long postId,
			Throwable ex
			) {
		log.error("Comment Service Fallback Triggered for Post: {}",postId,ex);
		return CommentResponseDto.builder()
				.postId(postId)
				.commentCount(0L)
				.build();
	}
}