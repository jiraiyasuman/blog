package com.blog_post.service;

import com.blog_post.dto.CreatePostRequest;
import com.blog_post.dto.DeletePostResponse;
import com.blog_post.dto.PostCommandResponse;
import com.blog_post.dto.UpdatePostRequest;

public interface PostCommandService {

    PostCommandResponse createPost(

            Long userId,

            CreatePostRequest request
    );

    PostCommandResponse updatePost(

            Long postId,

            Long userId,

            UpdatePostRequest request
    );

    DeletePostResponse deletePost(

            Long postId,

            Long userId
    );
}
