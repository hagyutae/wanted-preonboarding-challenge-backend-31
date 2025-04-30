package com.mkhwang.wantedcqrs.product.domain.dto;

import com.mkhwang.wantedcqrs.product.domain.ProductStatus;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

import java.time.Instant;

@Getter
public class ProductSearchResultDto {
  private final Long id;
  private final String name;
  private final String slug;
  private final String shortDescription;
  private final String fullDescription;
  private final SellerDto seller;
  private final BrandDto brand;
  private final ProductStatus status;
  private final Instant createdAt;
  private final ProductSearchImageDto image;

  @QueryProjection
  public ProductSearchResultDto(Long id, String name, String slug, String shortDescription, String fullDescription,
                                SellerDto seller, BrandDto brand, ProductStatus status, Instant createdAt, ProductSearchImageDto image) {
    this.id = id;
    this.name = name;
    this.slug = slug;
    this.shortDescription = shortDescription;
    this.fullDescription = fullDescription;
    this.seller = seller;
    this.brand = brand;
    this.status = status;
    this.createdAt = createdAt;
    this.image = image;
  }
}
