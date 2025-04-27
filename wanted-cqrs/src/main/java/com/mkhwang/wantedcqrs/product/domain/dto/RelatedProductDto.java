package com.mkhwang.wantedcqrs.product.domain.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class RelatedProductDto {

  private Long id;
  private String name;
  private String slug;
  private String shortDescription;
  private ProductSearchImageDto primaryImage;
  private ProductSearchImageDto secondaryImage;
  private BigDecimal basePrice;
  private BigDecimal salePrice;
  private String currency;
}