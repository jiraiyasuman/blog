package com.blog_post.service.impl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.blog_post.dto.CachedFeedDto;
import com.blog_post.dto.CachedPostDto;
import com.blog_post.dto.FeedResponse;
import com.blog_post.dto.PostResponse;
import com.blog_post.dto.PostSummaryResponse;
import com.blog_post.dto.TrendingPostResponse;
import com.blog_post.entity.PostReadEntity;
import com.blog_post.exception.ResourceNotFoundException;
import com.blog_post.mapper.PostQueryMapper;
import com.blog_post.repository.PostReadRepository;
import com.blog_post.service.CacheService;
import com.blog_post.service.PostQueryService;
import com.blog_post.sharding.ShardContext;
import com.blog_post.sharding.ShardResolver;
import com.blog_post.util.PaginationUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostQueryServiceImpl implements PostQueryService {

    private final PostReadRepository postReadRepository;
    private final CacheService cacheService;

    @Override
    @Transactional(readOnly = true)
    public PostResponse getPost(Long postId) {

        CachedPostDto cached = cacheService.getPost(postId);

        if (cached != null) {

            log.info("Fetching post {} from cache", postId);

            return PostResponse.builder()
                    .postId(cached.getPostId())
                    .userId(cached.getUserId())
                    .title(cached.getTitle())
                    .content(cached.getComment())
                    .likeCount(cached.getLikeCount())
                    .commentCount(cached.getCommentCount())
                    .createdAt(cached.getCreatedAt())
                    .build();
        }

        ShardResolver.useReadDb();

        try {

            PostReadEntity entity = postReadRepository.findById(postId)
                    .orElseThrow(() ->
                            new ResourceNotFoundException(
                                    "Post not found with id: " + postId
                            )
                    );

            cacheService.cachePost(buildCachedPost(entity));

            return PostQueryMapper.mapToResponse(entity);

        } finally {
            ShardContext.clear();
        }
    }

    @Override
    @Transactional(readOnly = true)
    public FeedResponse getFeed(int page, int size) {

        CachedFeedDto cached = cacheService.getFeed(page);

        if (cached != null) {

            log.info("Fetching feed page {} from cache", page);

            return FeedResponse.builder()
                    .posts(
                            cached.getPosts()
                                    .stream()
                                    .map(this::mapToSummaryResponse)
                                    .toList()
                    )
                    .currentPage(cached.getCurrentPage())
                    .hasNext(cached.getHasNext())
                    .build();
        }

        ShardResolver.useReadDb();

        try {

            PageRequest pageable =
                    PaginationUtil.defaultPageRequest(page, size);

            Page<PostReadEntity> posts =
                    postReadRepository.findAllByOrderByCreatedAtDesc(pageable);

            List<PostSummaryResponse> content =
                    posts.getContent()
                            .stream()
                            .map(PostQueryMapper::mapToSummary)
                            .toList();

            cacheService.cacheFeed(
                    page,
                    CachedFeedDto.builder()
                            .posts(
                                    posts.getContent()
                                            .stream()
                                            .map(this::buildCachedPost)
                                            .toList()
                            )
                            .currentPage(page)
                            .hasNext(posts.hasNext())
                            .build()
            );

            return FeedResponse.builder()
                    .posts(content)
                    .currentPage(page)
                    .totalPages(posts.getTotalPages())
                    .totalElements(posts.getTotalElements())
                    .hasNext(posts.hasNext())
                    .build();

        } finally {
            ShardContext.clear();
        }
    }

    @Override
    @Transactional(readOnly = true)
    public FeedResponse getUserPosts(
            Long userId,
            int page,
            int size
    ) {

        ShardResolver.useReadDb();

        try {

            Page<PostReadEntity> posts =
                    postReadRepository.findByUserIdOrderByCreatedAtDesc(
                            userId,
                            PaginationUtil.defaultPageRequest(page, size)
                    );

            return FeedResponse.builder()
                    .posts(
                            posts.getContent()
                                    .stream()
                                    .map(PostQueryMapper::mapToSummary)
                                    .toList()
                    )
                    .currentPage(page)
                    .totalPages(posts.getTotalPages())
                    .totalElements(posts.getTotalElements())
                    .hasNext(posts.hasNext())
                    .build();

        } finally {
            ShardContext.clear();
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<TrendingPostResponse> getTrendingPosts() {

        CachedFeedDto cached = cacheService.getTrending();

        if (cached != null) {

            log.info("Fetching trending posts from cache");

            return cached.getPosts()
                    .stream()
                    .map(this::mapToTrendingResponse)
                    .toList();
        }

        ShardResolver.useReadDb();

        try {

            List<TrendingPostResponse> trending =
                    postReadRepository.findTrendingPosts(
                                    PageRequest.of(0, 10)
                            )
                            .stream()
                            .map(this::mapEntityToTrendingResponse)
                            .toList();

            return trending;

        } finally {
            ShardContext.clear();
        }
    }

    private CachedPostDto buildCachedPost(PostReadEntity entity) {

        return CachedPostDto.builder()
                .postId(entity.getPostId())
                .userId(entity.getUserId())
                .title(entity.getTitle())
                .comment(entity.getContent())
                .likeCount(entity.getLikeCount())
                .commentCount(entity.getCommentCount())
                .createdAt(entity.getCreatedAt())
                .build();
    }

    private PostSummaryResponse mapToSummaryResponse(
            CachedPostDto post
    ) {

        return PostSummaryResponse.builder()
                .postId(post.getPostId())
                .title(post.getTitle())
                .likeCount(post.getLikeCount())
                .commentCount(post.getCommentCount())
                .createdAt(post.getCreatedAt())
                .build();
    }

    private TrendingPostResponse mapToTrendingResponse(
            CachedPostDto post
    ) {

        return TrendingPostResponse.builder()
                .postId(post.getPostId())
                .title(post.getTitle())
                .likeCount(post.getLikeCount())
                .commentCount(post.getCommentCount())
                .trendingScore(
                        calculateTrendingScore(
                                post.getLikeCount(),
                                post.getCommentCount()
                        )
                )
                .build();
    }

    private TrendingPostResponse mapEntityToTrendingResponse(
            PostReadEntity post
    ) {

        return TrendingPostResponse.builder()
                .postId(post.getPostId())
                .title(post.getTitle())
                .likeCount(post.getLikeCount())
                .commentCount(post.getCommentCount())
                .trendingScore(
                        calculateTrendingScore(
                                post.getLikeCount(),
                                post.getCommentCount()
                        )
                )
                .build();
    }

    private double calculateTrendingScore(
            long likeCount,
            long commentCount
    ) {

        return (likeCount * 2.0)
                + (commentCount * 3.0);
    }
}