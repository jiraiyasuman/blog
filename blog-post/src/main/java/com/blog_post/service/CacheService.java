package com.blog_post.service;

import com.blog_post.dto.CachedFeedDto;
import com.blog_post.dto.CachedPostDto;

public interface CacheService {

    void cachePost(
            CachedPostDto dto
    );

    CachedPostDto getPost(
            Long postId
    );

    void evictPost(
            Long postId
    );

    void cacheFeed(
            int page,
            CachedFeedDto feed
    );

    CachedFeedDto getFeed(
            int page
    );

    void evictFeed(
            int page
    );

    void evictAllFeeds();

    void cacheTrending(
            CachedFeedDto feed
    );

    CachedFeedDto getTrending();

    void evictTrending();
}