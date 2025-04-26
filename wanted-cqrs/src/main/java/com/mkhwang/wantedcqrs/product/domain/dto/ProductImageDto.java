package com.mkhwang.wantedcqrs.product.domain.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

@Getter
public class ProductImageDto {
  private final Long id;
  private final String url;
  private final String altText;
  private final Boolean primary;
  private final Integer displayOrder;

  @QueryProjection
  public ProductImageDto(Long id, String url, String altText, Boolean primary, Integer displayOrder) {
    this.id = id;
    this.url = url;
    this.altText = altText;
    this.primary = primary;
    this.displayOrder = displayOrder;
  }
}
