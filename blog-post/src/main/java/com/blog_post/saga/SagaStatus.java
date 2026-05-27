package com.blog_post.saga;

public enum SagaStatus {

	STARTED, 
	IN_PROGRESS,
	COMPLETED,
	FAILED,
	COMPENSATING,
	COMPENSATED
}
