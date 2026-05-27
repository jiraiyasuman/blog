package com.blog_post.entity;

import jakarta.persistence.*;

import lombok.*;

import java.time.Instant;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "posts_read",

        indexes = {

                @Index(
                        name = "idx_post_created",
                        columnList = "createdAt"
                ),

                @Index(
                        name = "idx_post_user",
                        columnList = "userId"
                ),

                @Index(
                        name = "idx_post_likes",
                        columnList = "likeCount"
                ),

                @Index(
                        name = "idx_post_comments",
                        columnList = "commentCount"
                ),

                @Index(
                        name = "idx_post_trending",
                        columnList =
                                "likeCount,commentCount,createdAt"
                )
        }
)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostReadEntity {

    @Id
    private Long postId;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false,
            length = 255)
    private String title;

    @Lob
    @Column(nullable = false,
            columnDefinition = "LONGTEXT")
    private String content;

    @Column(nullable = false)
    private Long likeCount;

    @Column(nullable = false)
    private Long commentCount;

    @Column(nullable = false)
    private LocalDateTime createdAt;
}
