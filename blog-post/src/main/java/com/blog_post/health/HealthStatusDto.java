package com.blog_post.health;



import lombok.*;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HealthStatusDto {

private String serviceName;

private String url;

private ServiceStatus status;

private Integer statusCode;

private String message;

private Instant checkedAt;
}
