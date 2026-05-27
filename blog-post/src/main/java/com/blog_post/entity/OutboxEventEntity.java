package com.blog_post.entity;

import java.time.Instant;

import com.blog_post.outbox.OutboxStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
		name = "outbox_events",
		indexes = {
				@Index(
					name = "idx_outbox_status",
					columnList = "status"
						),
				@Index(
						name = "idx_outbox_created",
						columnList = "createdAt"
						)
		}
		)
@Getter@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OutboxEventEntity {

	@Id
	private Long id;
	@Column(nullable = false)
	private Long aggregateId;
	@Column(nullable = false)
	private String aggregateType;
	@Column(nullable = false)
	private String eventType;
	@Lob
	@Column(nullable=false)
	private String payload;
	@Enumerated(EnumType.STRING)
	private OutboxStatus status;
	@Column(nullable =false)
	private Instant createdAt;
	private Instant publishAt;
	private Integer retryCount;
	private String errorMessage;
}