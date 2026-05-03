package com.blog.blog_login_module.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.blog_login_module.entity.OutboxEvent;

public interface OutboxRepository extends JpaRepository<OutboxEvent, Long>{

	List<OutboxEvent> findByProcessedFalse();
}
