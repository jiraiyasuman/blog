package com.blog_post.post.outbox;

import java.util.List;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.blog_post.post.repository.OutboxRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OutboxPublisher {

	private final OutboxRepository repository;
	private final RabbitTemplate rabbitTemplate;
	@Scheduled(fixedDelay = 3000)
	public void publish() {
		List<OutboxEvent> events = repository.findByProcessedFalse();
		
		for(OutboxEvent e : events) {
			rabbitTemplate.convertAndSend(
					"post.exchange",
					"post.created",
					e.getPayload()
					);
			e.setProcessed(true);
			repository.save(e);
		}
	}
	
}
