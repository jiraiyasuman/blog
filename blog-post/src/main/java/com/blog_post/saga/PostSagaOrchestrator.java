package com.blog_post.saga;

import java.util.UUID;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.blog_post.kafka.KafkaUtil;
import com.blog_post.kafka.SagaEvent;
import com.blog_post.service.SagaService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class PostSagaOrchestrator {

	private final SagaService sagaService;
	private final KafkaTemplate<String, Object> kafkaTemplate;
	
	public void startPostCreationSaga(Long postId, Long userId) {
	String sagaId = UUID.randomUUID().toString();
	SagaRequest request = SagaRequest.builder()
			.sagaId(sagaId)
			.postId(postId)
			.userId(userId)
			.eventType("POST_CREATED")
			.build(); 
	sagaService.startSaga(request);
	SagaEvent event = SagaEvent.builder()
			          .sagaId(sagaId)
			          .postId(postId)
			          .userId(userId)
			          .eventType("POST_CREATED")
			          .build();
	
	kafkaTemplate.send(KafkaUtil.SAGA_TOPIC,
			sagaId,
			event
			);
	
	log.info("Saga Orchestration Started: {}",sagaId);
	}
}
