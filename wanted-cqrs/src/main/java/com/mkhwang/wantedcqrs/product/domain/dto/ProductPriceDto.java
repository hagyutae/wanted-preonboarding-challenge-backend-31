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

  public ProductPriceDto(BigDecimal basePrice, BigDecimal salePrice, BigDecimal costPrice, String currency, BigDecimal taxRate) {
    this.basePrice = basePrice;
    this.salePrice = salePrice;
    this.costPrice = costPrice;
    this.currency = currency;
    this.taxRate = taxRate;
  }
}
