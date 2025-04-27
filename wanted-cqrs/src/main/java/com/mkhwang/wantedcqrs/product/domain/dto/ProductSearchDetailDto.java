package com.mkhwang.wantedcqrs.product.domain.dto;

import com.mkhwang.wantedcqrs.product.domain.ProductStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.List;

@Setter
@Getter
public class ProductSearchDetailDto {
  private Long id;
  private String name;
  private String slug;
  private String shortDescription;
  private String fullDescription;
  private ProductStatus status;
  private Instant createdAt;
  private Instant updatedAt;

  private SellerDto seller;
  private BrandDto brand;

  private ProductDetailDto detail;
  private ProductPriceDto price;

  private List<CategoryDto> categories;
  private List<ProductOptionGroupDto> optionGroups;
  private List<ProductImageDto> images;
  private List<TagDto> tags;
  private ProductRatingDto rating;
  private List<RelatedProductDto> relatedProducts;
}
