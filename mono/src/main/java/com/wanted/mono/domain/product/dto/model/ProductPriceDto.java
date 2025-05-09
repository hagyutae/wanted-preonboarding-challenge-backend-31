package com.wanted.mono.domain.product.dto.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductPriceDto {
    @JsonProperty("base_price")
    private BigDecimal basePrice;
    @JsonProperty("sale_price")
    private BigDecimal salePrice;
    private String currency;
    @JsonProperty("tax_rate")
    private BigDecimal taxRate;
    @JsonProperty("discount_percentage")
    private BigDecimal discountPercentage;

    public ProductPriceDto(BigDecimal basePrice, BigDecimal salePrice, String currency, BigDecimal taxRate) {
        this.basePrice = basePrice;
        this.salePrice = salePrice;
        this.currency = currency;
        this.taxRate = taxRate;
    }

    public void addDiscountPercentage() {
        if (basePrice != null && salePrice != null && BigDecimal.ZERO.compareTo(basePrice) != 0) {
            this.discountPercentage = basePrice.subtract(salePrice)
                    .multiply(BigDecimal.valueOf(100))
                    .divide(basePrice, 0, RoundingMode.FLOOR);
        } else {
            this.discountPercentage = BigDecimal.ZERO;
        }
    }
}
