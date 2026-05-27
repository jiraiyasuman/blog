package com.blog_post.service;

import java.util.List;

import com.blog_post.dto.FeedResponse;
import com.blog_post.dto.PostResponse;
import com.blog_post.dto.TrendingPostResponse;

public interface PostQueryService {

    PostResponse getPost(
            Long postId
    );

    FeedResponse getFeed(

            int page,

            int size
    );

    FeedResponse getUserPosts(

            Long userId,

            int page,

            int size
    );

    List<TrendingPostResponse>
    getTrendingPosts();
}