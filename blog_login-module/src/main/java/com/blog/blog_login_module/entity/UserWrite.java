package com.blog.blog_login_module.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users", indexes = {
        @Index(name = "idx_users_email", columnList = "email"),
        @Index(name = "idx_users_username", columnList = "username"),
        @Index(name = "idx_users_shard", columnList = "shard_key")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserWrite extends BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password; // store BCrypt hash

    @Column(nullable = false, unique = true)
    private String username;

    private boolean enabled;

    private boolean locked;

    private LocalDateTime lockTime;

    private int failedAttempts;

    @Column(name = "shard_key", nullable = false)
    private String shardKey;

    @Version
    private Integer version;
}