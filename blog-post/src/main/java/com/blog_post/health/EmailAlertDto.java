package com.blog_post.health;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmailAlertDto {

    private String recipient;

    private String subject;

    private String body;
}