package com.mkhwang.wantedcqrs.product.domain.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProductOptionDto {

  private Long id;
  private String name;
  private BigDecimal additionalPrice;
  private String sku;
  private Integer stock;
  private Integer displayOrder;
}
