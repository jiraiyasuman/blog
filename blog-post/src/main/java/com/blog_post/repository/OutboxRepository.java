package com.blog_post.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.blog_post.entity.OutboxEventEntity;
import com.blog_post.outbox.OutboxStatus;

import feign.Param;

public interface OutboxRepository extends JpaRepository<OutboxEventEntity, Long>{

	List<OutboxEventEntity> findTop100ByStatusOrderByCreatedAtAsc(
			OutboxStatus status
			);
	 @Query("""
	            SELECT o
	            FROM OutboxEventEntity o
	            WHERE o.status = :status
	            AND o.retryCount < 5
	            ORDER BY o.createdAt ASC
	            """)
	
	List<OutboxEventEntity> findRetryEvents( @Param("status")OutboxStatus status);

}
