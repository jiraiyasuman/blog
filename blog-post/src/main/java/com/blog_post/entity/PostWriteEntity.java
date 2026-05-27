package com.blog_post.entity;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

import com.blog_post.common.BaseModel;

@Entity
@Table(
    name = "posts_write",
    indexes = {
        @Index(
            name = "idx_post_user",
            columnList = "userId"
        )
    }
)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostWriteEntity extends BaseModel {

    @Id
    private Long postId;

    @Column(nullable = false)
    private Long userId;

    @Column(
        nullable = false,
        length = 255
    )
    private String title;

    @Lob
    @Column(
        nullable = false,
        columnDefinition = "LONGTEXT"
    )
    private String content;

    @Column(nullable = false)
    private Boolean deleted;

    private Instant createdAt;
}