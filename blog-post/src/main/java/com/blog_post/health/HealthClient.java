package com.blog_post.health;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class HealthClient {

    private final RestTemplate
            restTemplate =
            new RestTemplate();

    public ResponseEntity<String>
    checkHealth(
            String url
    ) {

        return restTemplate.getForEntity(
                url,
                String.class
        );
    }
}