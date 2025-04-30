package com.mkhwang.wantedcqrs.product.domain.dto.product;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mkhwang.wantedcqrs.product.domain.ProductStatus;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProductCreateRequestDto {
  @NotEmpty
  private String name;
  @NotEmpty
  private String slug;
  @JsonProperty("short_description")
  private String shortDescription;
  @JsonProperty("full_description")
  private String fullDescription;
  @NotNull
  @JsonProperty("seller_id")
  private Long sellerId;
  @NotNull
  @JsonProperty("brand_id")
  private Long brandId;

  private ProductStatus status = ProductStatus.ACTIVE;

  private ProductDetailCreateDto detail;
  private ProductPriceCreateDto price;
  private List<ProductCategoryCreateDto> categories;
  @JsonProperty("option_groups")
  private List<ProductOptionGroupCreateDto> optionGroups;
  private List<ProductImageCreateDto> images;
  private List<Long> tags;
}
