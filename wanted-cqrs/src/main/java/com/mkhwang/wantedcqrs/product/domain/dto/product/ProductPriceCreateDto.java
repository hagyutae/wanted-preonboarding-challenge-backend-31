package com.mkhwang.wantedcqrs.product.domain.dto.product;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProductPriceCreateDto {

  @Min(0)
  @JsonProperty("base_price")
  private BigDecimal basePrice;
  @Min(0)
  @JsonProperty("sale_price")
  private BigDecimal salePrice;
  @Min(0)
  @JsonProperty("cost_price")
  private BigDecimal costPrice;
  @NotEmpty
  private String currency;
  @Min(0)
  @JsonProperty("tax_rate")
  private BigDecimal taxRate;
}
