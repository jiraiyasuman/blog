package com.blog_post.service.impl;

import java.time.Duration;
import java.util.Set;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.blog_post.cache.CacheKeys;
import com.blog_post.dto.CachedFeedDto;
import com.blog_post.dto.CachedPostDto;
import com.blog_post.service.CacheService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
@Service
@Slf4j
@RequiredArgsConstructor
public class CacheServiceImpl implements CacheService{

	private RedisTemplate<String, Object> redisTemplate;
	@Value("${cache.ttl.posts}")
	private Long postTtl;
	@Value("${cache.ttl.feed}")
	private Long feedTtl;
	@Value("${cache.ttl.trending}")
	private Long trendingTtl;
	@Override
    public void cachePost(
            CachedPostDto dto
    ) {

        String key =
                CacheKeys.postkey(dto.getPostId());

        redisTemplate.opsForValue().set(
                key,
                dto,
                Duration.ofSeconds(postTtl)
        );

        log.info(
                "Post Cached: {}",
                dto.getPostId()
        );
    }

    @Override
    public CachedPostDto getPost(
            Long postId
    ) {

        String key =
                CacheKeys.postkey(postId);

        Object value =
                redisTemplate.opsForValue()
                        .get(key);

        if (value == null) {

            log.info(
                    "Cache MISS for Post: {}",
                    postId
            );

            return null;
        }

        log.info(
                "Cache HIT for Post: {}",
                postId
        );

        return (CachedPostDto) value;
    }

    @Override
    public void evictPost(
            Long postId
    ) {

        redisTemplate.delete(
                CacheKeys.postkey(postId)
        );

        log.info(
                "Post Cache Evicted: {}",
                postId
        );
    }

    @Override
    public void cacheFeed(
            int page,
            CachedFeedDto feed
    ) {

        redisTemplate.opsForValue().set(
                CacheKeys.feedKey(page),
                feed,
                Duration.ofSeconds(feedTtl)
        );

        log.info(
                "Feed Cached: page {}",
                page
        );
    }

    @Override
    public CachedFeedDto getFeed(
            int page
    ) {

        Object value =
                redisTemplate.opsForValue().get(
                        CacheKeys.feedKey(page)
                );

        return value == null
                ? null
                : (CachedFeedDto) value;
    }

    
    public void evictFeed(
            int page
    ) {

        redisTemplate.delete(
                CacheKeys.feedKey(page)
        );

        log.info(
                "Feed Cache Evicted: {}",
                page
        );
    }

    @Override
    public void evictAllFeeds() {

        Set<String> keys =
                redisTemplate.keys("feed:*");

        if (keys != null && !keys.isEmpty()) {

            redisTemplate.delete(keys);

            log.info(
                    "All Feed Cache Evicted"
            );
        }
    }

    @Override
    public void cacheTrending(
            CachedFeedDto feed
    ) {

        redisTemplate.opsForValue().set(
                CacheKeys.trendingKey(),
                feed,
                Duration.ofSeconds(trendingTtl)
        );

        log.info(
                "Trending Feed Cached"
        );
    }

    @Override
    public CachedFeedDto getTrending() {

        Object value =
                redisTemplate.opsForValue()
                        .get(
                                CacheKeys.trendingKey()
                        );

        return value == null
                ? null
                : (CachedFeedDto) value;
    }

    @Override
    public void evictTrending() {

        redisTemplate.delete(
                CacheKeys.trendingKey()
        );

        log.info(
                "Trending Cache Evicted"
        );
    }
}
