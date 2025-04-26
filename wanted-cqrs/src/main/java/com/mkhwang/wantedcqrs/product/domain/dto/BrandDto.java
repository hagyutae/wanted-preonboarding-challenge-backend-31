package com.mkhwang.wantedcqrs.product.domain.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

@Getter
public class BrandDto {
  private final Long id;
  private final String name;
  private final String slug;
//  private final String description;
  private final String logoUrl;
  private final String website;

  @QueryProjection
  public BrandDto(Long id, String name, String slug, String logoUrl, String website) {
    this.id = id;
    this.name = name;
    this.slug = slug;
    this.logoUrl = logoUrl;
    this.website = website;
  }
}
