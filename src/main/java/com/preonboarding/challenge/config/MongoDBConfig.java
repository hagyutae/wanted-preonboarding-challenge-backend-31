package com.preonboarding.challenge.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

@Configuration
@EnableMongoRepositories(basePackages = "com.preonboarding.challenge.service.query.repository")
public class MongoDBConfig {

    @Value("${spring.mongodb.host}")
    private String host;

    @Value("${spring.mongodb.port}")
    private int port;

    @Value("${spring.mongodb.username}")
    private String username;

    @Value("${spring.mongodb.password}")
    private String password;

    @Value("${spring.mongodb.database}")
    private String database;

    @Bean
    public MongoClient mongoClient() {
        String connectionString = String.format("mongodb://%s:%s@%s:%d",
                username, password, host, port);
        return MongoClients.create(connectionString);
    }

    @Bean
    public MongoTemplate mongoTemplate() {
        return new MongoTemplate(mongoClient(), database);
    }
}