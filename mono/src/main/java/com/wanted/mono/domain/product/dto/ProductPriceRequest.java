package com.wanted.mono.domain.product.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProductPriceRequest {

    @JsonProperty("base_price")
    private BigDecimal basePrice;

    @JsonProperty("sale_price")
    private BigDecimal salePrice;

    @JsonProperty("cost_price")
    private BigDecimal costPrice;

    private String currency;

    @JsonProperty("tax_rate")
    private BigDecimal taxRate;
}

