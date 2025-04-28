package com.mkhwang.wantedcqrs.product.domain.dto.review;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Map;

@Getter
@Setter
public class ReviewSearchSummaryDto {
  @JsonProperty("average_rating")
  private BigDecimal averageRating;
  @JsonProperty("total_count")
  private Integer totalCount;
  private Map<String, Integer> distribution;

  private ReviewSearchSummaryDto(BigDecimal averageRating, Integer totalCount, Map<String, Integer> distribution) {
    this.averageRating = averageRating;
    this.totalCount = totalCount;
    this.distribution = distribution;
  }

  public static ReviewSearchSummaryDto of(BigDecimal averageRating, Integer totalCount, Map<String, Integer> distribution) {
    return new ReviewSearchSummaryDto(averageRating, totalCount, distribution);
  }
}
