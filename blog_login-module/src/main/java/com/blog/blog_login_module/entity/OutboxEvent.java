package com.blog.blog_login_module.entity;

import java.time.LocalDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="outbox_event")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OutboxEvent {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	@Column(name="aggregate_type")
	private String aggregateType;
	@Column(name="aggregate_id")
	private Long aggregateId;
	@Column(name="event_type")
	private String eventType;
	@Column(name="payload")
	private String payload;
	@Column(name="processed")
	private boolean processed;
	@Column(name="created_at")
	private LocalDateTime createdAt;
}
