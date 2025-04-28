package com.mkhwang.wantedcqrs.product.domain.dto.category;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryProductImageDto {
  private String url;
  private String altText;

  @QueryProjection
  public CategoryProductImageDto(String url, String altText) {
    this.url = url;
    this.altText = altText;
  }
}
