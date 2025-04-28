package com.mkhwang.wantedcqrs.product.domain.dto.category;

import com.mkhwang.wantedcqrs.product.domain.ProductStatus;
import com.mkhwang.wantedcqrs.product.domain.dto.BrandDto;
import com.mkhwang.wantedcqrs.product.domain.dto.SellerDto;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CategoryProduct {

  private Long id;
  private String name;
  private String slug;
  private String shortDescription;
  private BigDecimal basePrice;
  private BigDecimal salePrice;
  private String currency;
  private CategoryProductImageDto primaryImage;
  private BrandDto brand;
  private SellerDto seller;

  private BigDecimal rating;
  private Long reviewCount;
  private Boolean inStock;
  private ProductStatus status;
  private Instant createdAt;

  @QueryProjection
  public CategoryProduct(Long id, String name, String slug, String shortDescription, BigDecimal basePrice,
                         BigDecimal salePrice, String currency, CategoryProductImageDto primaryImage,
                         BrandDto brand, SellerDto seller, ProductStatus status, Instant createdAt) {
    this.id = id;
    this.name = name;
    this.slug = slug;
    this.shortDescription = shortDescription;
    this.basePrice = basePrice;
    this.salePrice = salePrice;
    this.currency = currency;
    this.primaryImage = primaryImage;
    this.brand = brand;
    this.seller = seller;

    this.status = status;
    this.createdAt = createdAt;
  }

  public void setRating(Double rating) {
    this.rating = BigDecimal.valueOf(rating).setScale(2, RoundingMode.HALF_UP);
  }
}
