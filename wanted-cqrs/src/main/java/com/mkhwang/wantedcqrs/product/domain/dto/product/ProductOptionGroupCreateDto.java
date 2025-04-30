package com.mkhwang.wantedcqrs.product.domain.dto.product;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProductOptionGroupCreateDto {

  private String name;
  @JsonProperty("display_order")
  private int displayOrder;
  private List<ProductOptionCreateDto> options;
}
