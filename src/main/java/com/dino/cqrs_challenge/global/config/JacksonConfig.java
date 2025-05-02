package com.dino.cqrs_challenge.global.config;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JacksonConfig {

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer customJson() {
        return builder -> builder
                .propertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
    }
}