package com.blog_post.post.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.blog_post.post.outbox.OutboxEvent;

import java.util.List;

@Repository
public interface OutboxRepository extends JpaRepository<OutboxEvent, Long> {

    List<OutboxEvent> findByProcessedFalse();

    List<OutboxEvent> findTop100ByProcessedFalseOrderByIdAsc();

    List<OutboxEvent> findByAggregateId(Long aggregateId);

    List<OutboxEvent> findByType(String type);

    List<OutboxEvent> findByProcessedFalseAndType(String type);
}
