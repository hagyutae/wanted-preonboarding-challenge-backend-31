package com.mkhwang.wantedcqrs.product.domain.dto.review;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewUserDto {
  private Long id;
  private String name;
  @JsonProperty("avatar_url")
  private String avatarUrl;

  @QueryProjection
  public ReviewUserDto(Long id, String name, String avatarUrl) {
    this.avatarUrl = avatarUrl;
    this.id = id;
    this.name = name;
  }
}
