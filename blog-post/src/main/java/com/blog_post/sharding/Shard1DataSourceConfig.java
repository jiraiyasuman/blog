package com.blog_post.sharding;

import javax.activation.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.zaxxer.hikari.HikariDataSource;

@Configuration
public class Shard1DataSourceConfig {

	@Value("${spring.datasource.shard1.url}")
	private String url;
	@Value("${spring.datasource.shard1.username}")
	private String username;
	@Value("${spring.datasource.shard2.password}")
	private String password;
	@Bean(name="shard1DataSource")
	public DataSource shard1DataSource() {
		HikariDataSource dataSource = DataSourceBuilder.create()
				.type(HikariDataSource.class)
				.url(url)
				.username(username)
				.password(password)
				.build();
		dataSource.setMaximumPoolSize(20);
		dataSource.setMinimumIdle(5);
		return (DataSource) dataSource;
	}
}
