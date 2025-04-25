package com.mkhwang.wantedcqrs;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisIndexedHttpSession;

@OpenAPIDefinition(info = @Info(title = "Wanted CQRS API", version = "0.0",
        description = "Wanted CQRS service"))
@SpringBootApplication
@ConfigurationPropertiesScan
@EnableRedisIndexedHttpSession
public class WantedCqrsApplication {

  public static void main(String[] args) {
    SpringApplication.run(WantedCqrsApplication.class, args);
  }

}
