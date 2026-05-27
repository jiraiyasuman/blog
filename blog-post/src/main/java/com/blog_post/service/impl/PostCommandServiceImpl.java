package com.blog_post.service.impl;

import java.time.Instant;


import org.springframework.stereotype.Service;

import com.blog_post.dto.CreatePostRequest;
import com.blog_post.dto.DeletePostResponse;
import com.blog_post.dto.PostCommandResponse;
import com.blog_post.dto.UpdatePostRequest;
import com.blog_post.entity.OutboxEventEntity;
import com.blog_post.entity.PostWriteEntity;

import com.blog_post.exception.ResourceNotFoundException;

import com.blog_post.kafka.PostCreatedEvent;
import com.blog_post.mapper.PostCommandMapper;
import com.blog_post.outbox.OutboxStatus;
import com.blog_post.repository.PostWriteRepository;
import com.blog_post.saga.PostSagaOrchestrator;
import com.blog_post.service.CacheService;
import com.blog_post.service.OutboxService;
import com.blog_post.service.PostCommandService;
import com.blog_post.sharding.ShardKey;
import com.blog_post.util.JsonUtil;
import com.blog_post.util.KafkaTopics;
import com.blog_post.util.SnowflakeIdGenerator;
import com.blog_post.util.ValidationUtil;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostCommandServiceImpl
        implements PostCommandService {

    private final PostWriteRepository postWriteRepository;

    private final SnowflakeIdGenerator snowflakeIdGenerator;

    private final OutboxService outboxService;

    private final CacheService cacheService;

    private final PostSagaOrchestrator sagaOrchestrator;

    @Override
    @ShardKey
    @Transactional
    public PostCommandResponse createPost(
            Long userId,
            CreatePostRequest request
    ) {

        ValidationUtil.validatePostTitle(
                request.getTitle()
        );

        ValidationUtil.validateContent(
                request.getContent()
        );

        Long postId =
                snowflakeIdGenerator.nextId();

        PostWriteEntity entity =
                PostWriteEntity.builder()

                        .postId(postId)

                        .userId(userId)

                        .title(request.getTitle())

                        .content(request.getContent())

                        .deleted(false)

                        .build();

        postWriteRepository.save(entity);

        log.info(
                "Post Created: {}",
                postId
        );

        PostWriteEntity event =
                PostCreatedEvent.builder()

                        .postId(postId)

                        .userId(userId)

                        .title(request.getTitle())

                        .content(request.getContent())

                        .createdAt(Instant.now())

                        .build();

        OutboxEventEntity outbox =
                OutboxEventEntity.builder()

                        .id(
                                snowflakeIdGenerator
                                        .nextId()
                        )

                        .aggregateId(postId)

                        .aggregateType("POST")

                        .eventType(
                                KafkaTopics.POST_CREATED_TOPIC
                        )

                        .payload(
                                JsonUtil.toJson(event)
                        )

                        .status(
                                OutboxStatus.PENDING
                        )

                        .createdAt(
                                Instant.now()
                        )

                        .retryCount(0)

                        .build();

        outboxService.saveEvent(outbox);

        sagaOrchestrator.startPostCreationSaga(
                postId,
                userId
        );

        cacheService.evictAllFeeds();

        return PostCommandMapper
                .mapToResponse(entity);
    }

    @Override
    @ShardKey
    @Transactional
    public PostCommandResponse updatePost(
            Long postId,
            Long userId,
            UpdatePostRequest request
    ) {

        PostWriteEntity entity =
                postWriteRepository.findById(postId)

                        .orElseThrow(() ->

                                new ResourceNotFoundException(
                                        "Post not found"
                                )
                        );

        if (!entity.getUserId()
                .equals(userId)) {

            throw new RuntimeException(
                    "Unauthorized update"
            );
        }

        ValidationUtil.validatePostTitle(
                request.getTitle()
        );

        ValidationUtil.validateContent(
                request.getContent()
        );

        entity.setTitle(
                request.getTitle()
        );

        entity.setContent(
                request.getContent()
        );

        postWriteRepository.save(entity);

        cacheService.evictPost(postId);

        cacheService.evictAllFeeds();

        log.info(
                "Post Updated: {}",
                postId
        );

        return PostCommandMapper
                .mapToResponse(entity);
    }

    @Override
    @ShardKey
    @Transactional
    public DeletePostResponse deletePost(
            Long postId,
            Long userId
    ) {

        PostWriteEntity entity =
                postWriteRepository.findById(postId)

                        .orElseThrow(() ->

                                new ResourceNotFoundException(
                                        "Post not found"
                                )
                        );

        if (!entity.getUserId()
                .equals(userId)) {

            throw new RuntimeException(
                    "Unauthorized delete"
            );
        }

        entity.setDeleted(true);

        postWriteRepository.save(entity);

        cacheService.evictPost(postId);

        cacheService.evictAllFeeds();

        log.info(
                "Post Deleted: {}",
                postId
        );

        return DeletePostResponse.builder()

                .postId(postId)

                .deleted(true)

                .build();
    }
}