package com.mkhwang.wantedcqrs.product.domain.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProductOptionGroupDto {
  private Long id;
  private String name;
  private int displayOrder;
  private List<ProductOptionDto> options;
}
