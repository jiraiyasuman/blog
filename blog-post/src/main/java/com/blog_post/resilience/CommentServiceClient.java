package com.blog_post.resilience;

import java.util.concurrent.CompletableFuture;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
@FeignClient(
        name = "comment-service"
)
public interface CommentServiceClient {

    @GetMapping("/api/v1/comments/post/{postId}")
    @CircuitBreaker(
            name = "commentService",
            fallbackMethod = "fallbackComments"
    )
    @Retry(name = "kafkaRetry")
    @Bulkhead(name = "commentService")
    @TimeLimiter(name = "commentService")
    CompletableFuture<CommentResponseDto>
    getCommentsByPostId(
            @PathVariable Long postId
    );

    default CompletableFuture<CommentResponseDto>
    fallbackComments(
            Long postId,
            Throwable ex
    ) {

        return CompletableFuture.completedFuture(

                CommentResponseDto.builder()
                        .postId(postId)
                        .commentCount(0L)
                        .build()
        );
    }
}