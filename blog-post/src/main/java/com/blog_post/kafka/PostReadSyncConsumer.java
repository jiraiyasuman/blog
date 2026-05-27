package com.blog_post.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.blog_post.entity.PostReadEntity;
import com.blog_post.repository.PostReadRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostReadSyncConsumer {

	
	private final PostReadRepository postReadRepository;
	
	@KafkaListener(
			topics = "post-created-topic",
			groupId = "post-read-sync-group"
			)
	public void syncReadModel(PostCreatedEvent event) {
		PostReadEntity  entity = PostReadEntity.builder().
		postId(event.getPostId())
		.userId(event.getUserId())
		.title(event.getTitle())
		.likeCount(0L)
		.commentCount(0L)
		.createdAt(event.getCreatedAt())
		.build();
		postReadRepository.save(entity);
		log.info("Read Model Synced: {}",event.getPostId());
	}
}
