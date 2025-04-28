package com.mkhwang.wantedcqrs.product.domain.dto.product;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProductOptionCreateDto {

  private String name;
  @JsonProperty("additional_price")
  private BigDecimal additionalPrice;
  private String sku;
  private int stock;
  @JsonProperty("display_order")
  private int displayOrder;
}
