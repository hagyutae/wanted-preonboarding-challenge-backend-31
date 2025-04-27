package com.mkhwang.wantedcqrs.product.domain.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Map;

@Getter
@Setter
public class ProductRatingDto {
  private BigDecimal average;
  private Integer count;
  private Map<String, Integer> distribution;
}
