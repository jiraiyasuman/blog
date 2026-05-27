package com.blog_post.service.impl;

import java.time.Instant;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.blog_post.entity.OutboxEventEntity;
import com.blog_post.kafka.OutboxEventProducer;
import com.blog_post.outbox.OutboxStatus;
import com.blog_post.repository.OutboxRepository;
import com.blog_post.service.OutboxService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
@Service
@Slf4j
public class OutboxServiceImpl implements OutboxService{

	private OutboxRepository outboxRepository;
	private OutboxEventProducer outboxEventProducer;
	@Autowired
	public OutboxServiceImpl(OutboxRepository outboxRepository, OutboxEventProducer outboxEventProducer) {
		super();
		this.outboxRepository = outboxRepository;
		this.outboxEventProducer = outboxEventProducer;
	}

	@Transactional
	@Override
	public void saveEvent(OutboxEventEntity event) {
		outboxRepository.save(event);
		log.info("Outbox Event saved: {}",event.getAggregateId());
	}

	@Override
	@Transactional
	public void processPendingEvents() {
		List<OutboxEventEntity> events = outboxRepository.findTop100ByStatusOrderByCreatedAtAsc(OutboxStatus.PENDING);
		for(OutboxEventEntity event : events) {
			try {
				outboxEventProducer.publishOutboxEvent(event);
				event.setStatus(OutboxStatus.PUBLISHED);
				event.setPublishAt(Instant.now());
				outboxRepository.save(event);
				log.info("Outbox Event Published: {}",event.getId());
			} catch (Exception e) {
				log.error("Outbox Publish failed: {}",event.getId());
				handleFailure(event,e);
			}
			
		}
	}
	
	public void handleFailure( OutboxEventEntity event,Exception ex) {
		Integer retries = event.getRetryCount() == null ? 0 :event.getRetryCount();
		retries ++;
		event.setRetryCount(retries);
		if(retries >= 5) {
			event.setStatus(OutboxStatus.FAILED);
		}else {
			event.setStatus(OutboxStatus.PENDING);
		}
		outboxRepository.save(event);
	}

}
