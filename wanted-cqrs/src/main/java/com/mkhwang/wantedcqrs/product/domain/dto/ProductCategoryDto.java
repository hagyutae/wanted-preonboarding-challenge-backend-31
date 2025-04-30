package com.mkhwang.wantedcqrs.product.domain.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProductCategoryDto {

  private Long id;
  private String name;
  private String slug;
  private boolean primary;
  private ProductCategoryParentDto parent;

}
