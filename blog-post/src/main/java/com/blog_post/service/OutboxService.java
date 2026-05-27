package com.blog_post.service;

import com.blog_post.entity.OutboxEventEntity;

public interface OutboxService {
	void saveEvent(OutboxEventEntity event);
	void processPendingEvents();
}
