package com.blog_post.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SagaConsumer {

	@KafkaListener(
			topics = "saga-topic",
			groupId = "saga-group"
			)
	public void consumeSagaEvent(
			SagaEvent event
			) {
		log.info("Saga Event Received: {}",event);
		
		if("FAILED".equals(event.getStatus())) {
			log.error("Saga failed for Post: {}",event.getPostId());
		}
	}
}
