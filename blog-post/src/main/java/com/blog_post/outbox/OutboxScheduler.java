package com.blog_post.outbox;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.blog_post.service.OutboxService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@EnableScheduling
public class OutboxScheduler {

	private final OutboxService outboxService;
	@Autowired
	public OutboxScheduler(OutboxService outboxService) {
		super();
		this.outboxService = outboxService;
	}
	@Scheduled(fixedDelay = 5000)
	public void publicEvents() {
		log.info("Processing Pending Outbox Events...");
		outboxService.processPendingEvents();
	}
	
}
