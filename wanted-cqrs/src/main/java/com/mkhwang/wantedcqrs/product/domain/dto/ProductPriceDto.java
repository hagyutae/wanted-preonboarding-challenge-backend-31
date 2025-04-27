package com.mkhwang.wantedcqrs.product.domain.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProductPriceDto {
  private BigDecimal basePrice;
  private BigDecimal salePrice;
  private BigDecimal costPrice;
  private String currency;
  private BigDecimal taxRate;
}
