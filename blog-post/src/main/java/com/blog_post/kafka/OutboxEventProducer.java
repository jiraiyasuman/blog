package com.blog_post.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.blog_post.entity.OutboxEventEntity;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class OutboxEventProducer {

	private final KafkaTemplate<String, Object> kafkaTemplate;
	
	public void publishOutboxEvent(
			OutboxEventEntity event
			) {
		kafkaTemplate.send(event.getEventType(),String.valueOf(event.getAggregateId()),
				event.getPayload());
		
		log.info("Outbox Event Published: {}",
				event.getAggregateId());
	}
}