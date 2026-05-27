package com.blog_post.common;

import lombok.*;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventWrapperDto<T> {

    private String eventId;

    private EventType eventType;

    private T payload;

    private Instant timestamp;

    private String sourceService;
}
