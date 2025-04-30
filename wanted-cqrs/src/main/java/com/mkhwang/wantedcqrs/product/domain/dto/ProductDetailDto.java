package com.mkhwang.wantedcqrs.product.domain.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductDetailDto {
  private BigDecimal weight;
  private Map<String, Object> dimensions;
  private String materials;
  private String countryOfOrigin;
  private String warrantyInfo;
  private String careInstructions;
  private Map<String, Object> additionalInfo;

}
