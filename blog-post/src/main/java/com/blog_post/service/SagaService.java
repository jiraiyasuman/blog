package com.blog_post.service;

import com.blog_post.saga.SagaRequest;
import com.blog_post.saga.SagaResponse;

public interface SagaService {
	SagaResponse startSaga(SagaRequest request);
	void completeSaga(String sagaId);
	void failSaga(String sagaId,String reason);
}
