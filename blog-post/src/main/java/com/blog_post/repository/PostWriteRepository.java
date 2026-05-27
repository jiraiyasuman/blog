package com.blog_post.repository;

import org.springframework.data.jpa.repository
.JpaRepository;

import com.blog_post.entity.PostWriteEntity;

import java.util.List;

public interface PostWriteRepository extends
JpaRepository<PostWriteEntity, Long> {

List<PostWriteEntity>
findByUserId(Long userId);
}