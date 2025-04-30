package com.mkhwang.wantedcqrs.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

  @Bean
  public ModelMapper modelMapper() {
    ModelMapper mapper = new ModelMapper();
    mapper.getConfiguration()
      .setMatchingStrategy(MatchingStrategies.STRICT) // 엄격한 매칭 전략
      .setFieldMatchingEnabled(true) // 필드 매칭 활성화
      .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE); // private 필드에 접근 가능하도록 설정
    return mapper;
  }
}
