package com.blog_post.repository;



import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository
.JpaRepository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import org.springframework.data.repository.query.Param;

import com.blog_post.entity.PostReadEntity;

import java.util.List;

public interface PostReadRepository extends
JpaRepository<PostReadEntity, Long> {

Page<PostReadEntity>
findAllByOrderByCreatedAtDesc(
    Pageable pageable
);

Page<PostReadEntity>
findByUserIdOrderByCreatedAtDesc(

    Long userId,

    Pageable pageable
);

@Query("""
    SELECT p
    FROM PostReadEntity p
    ORDER BY
    (p.likeCount * 2 +
     p.commentCount * 3) DESC
    """)
List<PostReadEntity>
findTrendingPosts(
    Pageable pageable
);

@Modifying
@Query("""
    UPDATE PostReadEntity p
    SET p.likeCount =
        p.likeCount + 1
    WHERE p.postId = :postId
    """)
void incrementLikeCount(
    @Param("postId")
    Long postId
);

@Modifying
@Query("""
    UPDATE PostReadEntity p
    SET p.likeCount =
        p.likeCount - 1
    WHERE p.postId = :postId
    """)
void decrementLikeCount(
    @Param("postId")
    Long postId
);

@Modifying
@Query("""
    UPDATE PostReadEntity p
    SET p.commentCount =
        p.commentCount + 1
    WHERE p.postId = :postId
    """)
void incrementCommentCount(
    @Param("postId")
    Long postId
);

@Modifying
@Query("""
    UPDATE PostReadEntity p
    SET p.commentCount =
        p.commentCount - 1
    WHERE p.postId = :postId
    """)
void decrementCommentCount(
    @Param("postId")
    Long postId
);
}