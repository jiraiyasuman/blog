package com.blog_post.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.blog_post.repository.PostReadRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class LikeEventConsumer {

	private final PostReadRepository postReadRepository;
	@KafkaListener(
			topics = "like-created-topic",
			groupId = "post-service-group"
			)
	public void consumeLikeCreated(LikeCreatedEvent event) {
		postReadRepository.incrementLikeCount(event.getPostId());
		
		log.info("Like Count Incremented: {}",event.getPostId());
	}
	@KafkaListener(
			topics = "like-deleted-topic",
			groupId = "post-service-group"
			)
	public void consumeLikeDeleted(
			LikeDeletedEvent event
			) {
		postReadRepository.decrementLikeCount(
				event.getPostId()
				);
		log.info("Like Count Decrement: {}",event.getPostId());
	}
}



