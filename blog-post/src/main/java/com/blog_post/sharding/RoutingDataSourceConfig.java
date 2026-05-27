package com.blog_post.sharding;

import java.util.HashMap;
import java.util.Map;

import javax.activation.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;

@Configuration
public class RoutingDataSourceConfig {

	private final Environment environment;

    public RoutingDataSourceConfig(Environment environment) {
        this.environment = environment;
    }

    @Bean(name = "shard1")
    public DataSource shard1DataSource() {

        return (DataSource) DataSourceBuilder.create()
                .url(environment.getProperty("spring.datasource.shard1.url"))
                .username(environment.getProperty("spring.datasource.shard1.username"))
                .password(environment.getProperty("spring.datasource.shard1.password"))
                .driverClassName(
                        environment.getProperty(
                                "spring.datasource.shard1.driver-class-name"
                        )
                )
                .build();
    }

    @Bean(name = "shard2")
    public DataSource shard2DataSource() {

        return (DataSource) DataSourceBuilder.create()
                .url(environment.getProperty("spring.datasource.shard2.url"))
                .username(environment.getProperty("spring.datasource.shard2.username"))
                .password(environment.getProperty("spring.datasource.shard2.password"))
                .driverClassName(
                        environment.getProperty(
                                "spring.datasource.shard2.driver-class-name"
                        )
                )
                .build();
    }

    @Bean(name = "shard3")
    public DataSource shard3DataSource() {

        return (DataSource) DataSourceBuilder.create()
                .url(environment.getProperty("spring.datasource.shard3.url"))
                .username(environment.getProperty("spring.datasource.shard3.username"))
                .password(environment.getProperty("spring.datasource.shard3.password"))
                .driverClassName(
                        environment.getProperty(
                                "spring.datasource.shard3.driver-class-name"
                        )
                )
                .build();
    }

    @Bean
    public DataSource routingDataSource(
            @Qualifier("shard1") DataSource shard1,
            @Qualifier("shard2") DataSource shard2,
            @Qualifier("shard3") DataSource shard3
    ) {

        Map<Object, Object> targetDataSources = new HashMap<>();

        targetDataSources.put("SHARD_1", shard1);
        targetDataSources.put("SHARD_2", shard2);
        targetDataSources.put("SHARD_3", shard3);

        ShardRoutingDataSource routingDataSource =
                new ShardRoutingDataSource();

        routingDataSource.setTargetDataSources(targetDataSources);

        routingDataSource.setDefaultTargetDataSource(shard1);

        return (DataSource) routingDataSource;
    }
	
}
