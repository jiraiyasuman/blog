package com.blog_post.entity;

import java.time.Instant;

import com.blog_post.saga.SagaStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Entity
@Table(
		name= "saga_state_entity",
		indexes = {
				@Index(
						name="idx_saga_status",
						columnList = "status"
						)
		}
		)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SagaStateEntity {

	@Id
	private String sagaId;
	private Long postId;
	private Long userId;
	@Enumerated(EnumType.STRING)
	private SagaStatus status;
	
	private String currentStep;
	private String failureReason;
	private Instant createdAt;
	private Instant updatedAt;
}
