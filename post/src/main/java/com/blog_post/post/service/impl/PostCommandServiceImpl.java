package com.blog_post.post.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.annotation.Transactional;

import com.blog_post.post.dto.PostRequest;
import com.blog_post.post.dto.PostResponse;
import com.blog_post.post.entity.Post;
import com.blog_post.post.repository.OutboxRepository;
import com.blog_post.post.repository.PostReadRepository;
import com.blog_post.post.repository.PostRepository;
import com.blog_post.post.service.PostCommandService;
import com.blog_post.post.sharding.ShardResolver;
import com.blog_post.post.sharding.ShardRoutingDataSource;
import com.blog_post.post.sharding.SnowflakeGenerator;

public class PostCommandServiceImpl implements PostCommandService {

	private PostRepository postRepository;
	private PostReadRepository readRepository;
	private SnowflakeGenerator snowflakeGenerator;
	private ShardResolver shardResolver;
	private OutboxRepository outboxRepository;
	private RedisTemplate<String,Object> redisTemplate;
	
	@Autowired
	public PostCommandServiceImpl(PostRepository postRepository, PostReadRepository readRepository,
			SnowflakeGenerator snowflakeGenerator, ShardResolver shardResolver, OutboxRepository outboxRepository,
			RedisTemplate<String, Object> redisTemplate) {
		super();
		this.postRepository = postRepository;
		this.readRepository = readRepository;
		this.snowflakeGenerator = snowflakeGenerator;
		this.shardResolver = shardResolver;
		this.outboxRepository = outboxRepository;
		this.redisTemplate = redisTemplate;
	}
	@Override
	@Transactional
	@Async
	public PostResponse createPost(PostRequest request, String userId) {
		long id = snowflakeGenerator.nextId();
		String shard = shardResolver.resolve(id);
		ShardRoutingDataSource.setShard(shard);
		//Post post = Post.builder()
				
		
		
		return null;
	}

}
