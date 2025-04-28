package com.mkhwang.wantedcqrs.product.domain.dto.product;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductCategoryCreateDto {

  @JsonProperty("category_id")
  private Long categoryId;
  @JsonProperty("is_primary")
  private boolean primary;
}
