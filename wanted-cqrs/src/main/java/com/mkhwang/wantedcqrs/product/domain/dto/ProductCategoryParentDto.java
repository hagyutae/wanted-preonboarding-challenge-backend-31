package com.mkhwang.wantedcqrs.product.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductCategoryParentDto {

  private Long id;
  private String name;
  private String slug;


  public static ProductCategoryParentDto of(
          Long id, String name, String slug) {
    return new ProductCategoryParentDto(id, name, slug);
  }
}
