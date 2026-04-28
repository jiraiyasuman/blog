package com.blog_post.post.outbox;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity(name="outbox_event")
public class OutboxEvent {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private long id;
	@Column(name="type")
	private String type;
	@Column(name="aggregate_id")
	private long aggregateId;
	@Column(name="payload")
	private String payload;
	@Column(name="processed")
	private boolean processed;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public long getAggregateId() {
		return aggregateId;
	}
	public void setAggregateId(long aggregateId) {
		this.aggregateId = aggregateId;
	}
	public String getPayload() {
		return payload;
	}
	public void setPayload(String payload) {
		this.payload = payload;
	}
	public boolean isProcessed() {
		return processed;
	}
	public void setProcessed(boolean processed) {
		this.processed = processed;
	}
	@Override
	public String toString() {
		return "OutboxEvent [id=" + id + ", type=" + type + ", aggregateId=" + aggregateId + ", payload=" + payload
				+ ", processed=" + processed + "]";
	}
	public OutboxEvent(long id, String type, long aggregateId, String payload, boolean processed) {
		super();
		this.id = id;
		this.type = type;
		this.aggregateId = aggregateId;
		this.payload = payload;
		this.processed = processed;
	}
	public OutboxEvent() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
