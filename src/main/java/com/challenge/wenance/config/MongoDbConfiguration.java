package com.challenge.wenance.config;

import com.mongodb.ConnectionString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;

import java.net.UnknownHostException;

@Configuration
public class MongoDbConfiguration {

    @Value("${spring.data.mongodb.uri}")
    private String mongodb_uri;

    @Bean
    public MongoDatabaseFactory getMongoDbFactory() throws UnknownHostException {
        return new SimpleMongoClientDatabaseFactory( new ConnectionString( mongodb_uri ) );
    }

    @Bean(name = "mongoTemplate")
    public MongoTemplate getMongoTemplate() throws UnknownHostException {
        MongoTemplate mongoTemplate = new MongoTemplate(getMongoDbFactory());
        return mongoTemplate;
    }
}
