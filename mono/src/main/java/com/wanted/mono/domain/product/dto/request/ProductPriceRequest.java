package com.wanted.mono.domain.product.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
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
    @NotNull(message = "기본 가격은 필수 항목입니다.")
    @DecimalMin(value = "0.01", message = "기본 가격은 0보다 커야 합니다.")
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

