package com.mkhwang.wantedcqrs.product.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class ReviewSearchItemDto {
  private Long id;
  private ReviewUserDto user;
  private Integer rating;
  private String title;
  private String content;
  @JsonProperty("created_at")
  private Instant createdAt;
  @JsonProperty("updated_at")
  private Instant updatedAt;
  @JsonProperty("verified_purchase")
  private Boolean verifiedPurchase;
  @JsonProperty("helpful_votes")
  private Integer helpfulVotes;

  @QueryProjection
  public ReviewSearchItemDto(Long id, ReviewUserDto user, Integer rating, String title, String content,
                             Instant createdAt, Instant updatedAt, Boolean verifiedPurchase, Integer helpfulVotes) {
    this.id = id;
    this.user = user;
    this.rating = rating;
    this.title = title;
    this.content = content;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
    this.verifiedPurchase = verifiedPurchase;
    this.helpfulVotes = helpfulVotes;
  }
}
