package com.blog_post.outbox;

import java.util.concurrent.CompletableFuture;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import com.blog_post.entity.OutboxEventEntity;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class OutboxEventProducer {

	private final KafkaTemplate<String, Object> kafkaTemplate;
	
	public void publishOutboxEvent( OutboxEventEntity event ) {
		CompletableFuture<SendResult<String,Object>> future = kafkaTemplate.send(
				event.getEventType(),
				String.valueOf(event.getAggregateId()),
		        event.getPayload()
				);
		future.whenComplete((result, ex) -> {

            if (ex != null) {

                log.error(
                        "Kafka Publish Failed: {}",
                        event.getId(),
                        ex
                );

            } else {

                log.info(
                        "Kafka Publish Success: {}",
                        event.getId()
                );
            }
        });
	}
}
