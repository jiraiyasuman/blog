package com.blog.blog_api_gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;
@EnableDiscoveryClient
@SpringBootApplication
public class BlogApiGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(BlogApiGatewayApplication.class, args);
	}

	@Bean
	public RouteLocator blogPostRouteConfig(RouteLocatorBuilder routeLocatorBuilder) {
		return routeLocatorBuilder.routes()
				.route(p -> p
						.path("/blog/posts/**")
						.filters( f -> f.rewritePath("/blog/posts/(?<segment>.*)","/${segment}")
								.addResponseHeader("X-Response-Time", LocalDateTime.now().toString()))
						.uri("lb://POSTS"))
			.route(p -> p
					.path("/blog/like/**")
					.filters( f -> f.rewritePath("/blog/like/(?<segment>.*)","/${segment}")
							.addResponseHeader("X-Response-Time", LocalDateTime.now().toString()))
					.uri("lb://LIKE"))
			.route(p -> p
					.path("/blog/comments/**")
					.filters( f -> f.rewritePath("/blog/comments/(?<segment>.*)","/${segment}")
							.addResponseHeader("X-Response-Time", LocalDateTime.now().toString()))
					.uri("lb://COMMENTS")).build(); 
		
	}
}
