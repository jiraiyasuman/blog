package com.blog_post.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.blog_post.repository.PostReadRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentEventConsumer {
	private final PostReadRepository postReadRepository;
	@KafkaListener(
            topics = "comment-created-topic",
            groupId = "post-service-group"
    )
	public void consumerCommentCreated(CommentCreatedEvent event) {
	 postReadRepository.incrementCommentCount(event.getPostid());
	 
	 log.info("Comment Count Increment: {}",event.getPostid());
	}
	@KafkaListener(
			topics = "comment-deleted-topic",
			groupId = "post-service-group"
			)
	public void ConsumerConsumerDeleted(
			CommentDeletedEvent event
			) {
		postReadRepository.decrementCommentCount(
				event.getPostId()
				);
		log.info("Comment count Decremented: {}",event.getPostId());
	}
}
