package com.blog_post.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@Service
@RequiredArgsConstructor
public class PostEventProducer {

	private final KafkaTemplate<String, Object> kafkaTemplate;
	
	public void publishPostCreated(PostCreatedEvent postCreatedEvent) {
		
		kafkaTemplate.send(KafkaUtil.POST_CREATED_TOPIC,
				String.valueOf(postCreatedEvent.getPostId()),postCreatedEvent
				);
		log.info("Post Created Event Published: {}");
	}
	
	public void publishPostDeleted(PostDeletedEvent event) {
		kafkaTemplate.send(KafkaUtil.POST_DELETED_TOPIC,String.valueOf(event.getPostId()),event);
		log.info("Post Deleted Event Published: {}",event.getPostId());
	}
	
	public void publishPostUpdated(PostUpdatedEvent event) {
		kafkaTemplate.send(KafkaUtil.POST_UPDATED_TOPIC,String.valueOf(event),event);
		log.info("Post Updated event published: {}",event.getPostId());
	}
}
