package com.blog_post.post.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.blog_post.post.entity.PostRead;

import java.util.List;

@Repository
public interface PostReadRepository extends JpaRepository<PostRead, Long> {

    List<PostRead> findByUserId(String userId);

    Page<PostRead> findAll(Pageable pageable);

    Page<PostRead> findByUserId(String userId, Pageable pageable);

    List<PostRead> findByTitleContainingIgnoreCase(String title);

    List<PostRead> findTop10ByOrderByCreatedAtDesc();

    Page<PostRead> findAllByOrderByCreatedAtDesc(Pageable pageable);
}
