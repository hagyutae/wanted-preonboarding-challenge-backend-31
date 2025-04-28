package com.wanted.ecommerce.product.dto.response;

import java.math.BigDecimal;
import lombok.Builder;

@Builder
public record ProductPriceResponse(
    BigDecimal basePrice,
    BigDecimal salePrice,
    String currency,
    BigDecimal taxRate,
    Double discountPercentage
) {

    public static ProductPriceResponse of(BigDecimal basePrice, BigDecimal salePrice,
        String currency, BigDecimal taxRate, Double discountPercentage){
        return ProductPriceResponse.builder()
            .basePrice(basePrice)
            .salePrice(salePrice)
            .currency(currency)
            .taxRate(taxRate)
            .discountPercentage(discountPercentage)
            .build();
    }
}
