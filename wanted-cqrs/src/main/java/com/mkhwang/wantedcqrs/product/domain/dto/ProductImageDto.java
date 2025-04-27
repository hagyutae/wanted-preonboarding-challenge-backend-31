package com.mkhwang.wantedcqrs.product.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductImageDto {
  private Long id;
  private String url;
  private String altText;
  private Boolean primary;
  private Integer displayOrder;


}
