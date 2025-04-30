package com.mkhwang.wantedcqrs.product.domain.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

@Getter
public class ProductSearchImageDto {
  private final String url;
  private final String altText;

  @QueryProjection
  public ProductSearchImageDto(String url, String altText) {
    this.url = url;
    this.altText = altText;
  }
}
