package com.mkhwang.wantedcqrs.product.domain.dto.product;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Map;

@Getter
@Setter
public class ProductDetailCreateDto {

  private BigDecimal weight;
  private Map<String, Object> dimensions;
  private String materials;
  @JsonProperty("country_of_origin")
  private String countryOfOrigin;
  @JsonProperty("warranty_info")
  private String warrantyInfo;
  @JsonProperty("care_instructions")
  private String careInstructions;
  @JsonProperty("additional_info")
  private Map<String, Object> additionalInfo;
}
