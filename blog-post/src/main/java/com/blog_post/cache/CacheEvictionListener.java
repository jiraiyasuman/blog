package com.blog_post.cache;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.blog_post.kafka.CommentCreatedEvent;
import com.blog_post.kafka.LikeCreatedEvent;
import com.blog_post.service.CacheService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class CacheEvictionListener {

	private CacheService cacheService;
	@KafkaListener(
            topics = "like-created-topic",
            groupId = "cache-group"
    )
    public void handleLikeEvent(
            LikeCreatedEvent event
    ) {

        cacheService.evictPost(
                event.getPostId()
        );

        cacheService.evictTrending();

        log.info(
                "Cache Evicted for Like Event: {}",
                event.getPostId()
        );
    }

    @KafkaListener(
            topics = "comment-created-topic",
            groupId = "cache-group"
    )
    public void handleCommentEvent(
            CommentCreatedEvent event
    ) {

        cacheService.evictPost(
                event.getPostid()
        );

        cacheService.evictTrending();

        log.info(
                "Cache Evicted for Comment Event: {}",
                event.getPostid()
        );
    }
}
