package com.mkhwang.wantedcqrs.product.domain.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;

@Getter
@Setter
public class ProductRatingDto {
  private BigDecimal average;
  private Integer count;
  private Map<String, Integer> distribution;

  public ProductRatingDto(Double average, Integer count, Map<String, Integer> distribution) {
    this.average = BigDecimal.valueOf(average).setScale(2, RoundingMode.HALF_UP);
    this.count = count;
    this.distribution = distribution;
  }
}
