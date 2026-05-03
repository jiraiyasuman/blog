package com.blog.blog_login_module.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users_read", indexes = {
        @Index(name = "idx_users_read_email", columnList = "email"),
        @Index(name = "idx_users_read_username", columnList = "username")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRead extends BaseModel {

    @Id
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    private boolean enabled;

    private boolean locked;
}