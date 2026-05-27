package com.blog_post.sharding;

import javax.activation.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.zaxxer.hikari.HikariDataSource;

@Configuration
public class ReadReplicaDataSourceConfig {

	@Value("${string.datasource.read.url}")
	private String url;
	@Value("${string.datasource.read.username")
	private String username;
	@Value("${string.datasource.read.password}")
	private String password;
	@Bean(name="readReplicaDataSource")
	public DataSource readReplicaDataSource() {
		HikariDataSource dataSource = DataSourceBuilder.create().
				type(HikariDataSource.class).
				url(url)
				.username(username)
				.password(password)
				.build();
		dataSource.setMaximumPoolSize(20);
		dataSource.setMinimumIdle(5);
		dataSource.setReadOnly(true);
		return (DataSource)dataSource;
	}
	
}
