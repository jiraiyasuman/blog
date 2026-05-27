package com.blog_post.service.impl;

import java.time.Instant;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.blog_post.entity.SagaStateEntity;
import com.blog_post.repository.SagaStateRepository;
import com.blog_post.saga.CompensationHandler;
import com.blog_post.saga.SagaRequest;
import com.blog_post.saga.SagaResponse;
import com.blog_post.saga.SagaStatus;
import com.blog_post.service.SagaService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@Service
@RequiredArgsConstructor
public class SagaServiceImpl implements SagaService{

	private final SagaStateRepository sagaStateRepository;
	
	@Override
	@Transactional
	public SagaResponse startSaga(SagaRequest request) {
	 SagaStateEntity saga =	 SagaStateEntity.builder()
	 	.sagaId(request.getSagaId())
	 	.postId(request.getPostId())
	 	.userId(request.getUserId())
	 	.status(SagaStatus.STARTED)
	 	.currentStep("POST_CREATED")
	 	.createdAt(Instant.now())
	 	.updatedAt(Instant.now())
	 	.build();
	 	sagaStateRepository.save(saga);
	 	log.info("Saga Started: {}",request.getSagaId());
	 	return SagaResponse.builder()
	 			.sagaId(request.getSagaId())
	 			.status(SagaStatus.STARTED)
	 			.message("SAGA STARTED")
	 			.build();
	}

	@Transactional
	@Override
	public void completeSaga(String sagaId) {
		SagaStateEntity saga = sagaStateRepository.findById(sagaId)
				.orElseThrow();
		saga.setStatus(SagaStatus.COMPLETED);
		saga.setUpdatedAt(Instant.now());
		sagaStateRepository.save(saga);
		log.info("Saga Completed: {}",sagaId);
	}

	@Override
	@Transactional
	public void failSaga(String sagaId, String reason) {
		SagaStateEntity saga = sagaStateRepository.findById(sagaId)
				.orElseThrow();
		saga.setStatus(SagaStatus.FAILED);
		saga.setFailureReason(reason);
		saga.setUpdatedAt(Instant.now());
		sagaStateRepository.save(saga);
		CompensationHandler.rollbackPost(saga.getPostId());
		saga.setStatus(SagaStatus.COMPENSATED);
		log.error("Saga Failed and Compensated: {}",sagaId);
	}

}