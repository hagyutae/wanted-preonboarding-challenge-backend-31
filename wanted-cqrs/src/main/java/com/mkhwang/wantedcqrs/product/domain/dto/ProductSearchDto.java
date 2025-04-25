package com.mkhwang.wantedcqrs.product.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ProductSearchDto {
  private String keyword;
}
