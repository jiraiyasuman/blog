package com.blog_post.common;

import java.time.Instant;

import lombok.*;
 
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuditDto {

    private Long createdBy;

    private Long updatedBy;

    private Instant createdAt;

    private Instant updatedAt;
}