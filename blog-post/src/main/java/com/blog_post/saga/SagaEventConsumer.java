package com.blog_post.saga;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.blog_post.kafka.SagaEvent;
import com.blog_post.service.SagaService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class SagaEventConsumer {

	private final SagaService sagaService;
	
	@KafkaListener(
			topics = "saga-topic",
			groupId = "saga-group"
			)
	public void consumeSagaEvent(SagaEvent event) {
		log.info("Saga Event Recieved: {}",event);
		if("FAILED".equals(event.getStatus())) {
			sagaService.failSaga(event.getSagaId(), "Downstream Service Failure");
		}else if("COMPLETED".equals(event.getStatus())) {
			sagaService.completeSaga(event.getSagaId());
		}
	}
}
