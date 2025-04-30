package com.mkhwang.wantedcqrs.product.domain.dto.option;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProductOptionCreateRequestDto {
  @JsonProperty("option_group_id")
  private Long optionGroupId;
  private String name;
  @JsonProperty("additional_price")
  private BigDecimal additionalPrice;
  private String sku;
  private Integer stock;
  @JsonProperty("display_order")
  private int displayOrder;

}
