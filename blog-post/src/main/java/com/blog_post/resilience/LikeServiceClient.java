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
        name = "like-service"
)
public interface LikeServiceClient {

    @GetMapping("/api/v1/likes/post/{postId}")
    @CircuitBreaker(
            name = "likeService",
            fallbackMethod = "fallbackLikes"
    )
    @Retry(name = "kafkaRetry")
    @Bulkhead(name = "likeService")
    @TimeLimiter(name = "likeService")
    CompletableFuture<LikeResponseDto>
    getLikesByPostId(
            @PathVariable Long postId
    );

    default CompletableFuture<LikeResponseDto>
    fallbackLikes(
            Long postId,
            Throwable ex
    ) {

        return CompletableFuture.completedFuture(

                LikeResponseDto.builder()
                        .postId(postId)
                        .likeCount(0L)
                        .liked(false)
                        .build()
        );
    }
}