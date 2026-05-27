package com.blog_post.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog_post.dto.CreatePostRequest;
import com.blog_post.dto.DeletePostResponse;
import com.blog_post.dto.PostCommandResponse;
import com.blog_post.dto.UpdatePostRequest;
import com.blog_post.security.UserContext;
import com.blog_post.service.PostCommandService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class PostCommandController {

    private final PostCommandService
            postCommandService;

    @PostMapping
    public ResponseEntity<PostCommandResponse>
    createPost(

            @Valid
            @RequestBody
            CreatePostRequest request
    ) {

        Long userId =
                UserContext.getUserId();

        return ResponseEntity.ok(

                postCommandService.createPost(
                        userId,
                        request
                )
        );
    }

    @PutMapping("/{postId}")
    public ResponseEntity<PostCommandResponse>
    updatePost(

            @PathVariable Long postId,

            @Valid
            @RequestBody
            UpdatePostRequest request
    ) {

        Long userId =
                UserContext.getUserId();

        return ResponseEntity.ok(

                postCommandService.updatePost(
                        postId,
                        userId,
                        request
                )
        );
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<DeletePostResponse>
    deletePost(
            @PathVariable Long postId
    ) {

        Long userId =
                UserContext.getUserId();

        return ResponseEntity.ok(

                postCommandService.deletePost(
                        postId,
                        userId
                )
        );
    }
}