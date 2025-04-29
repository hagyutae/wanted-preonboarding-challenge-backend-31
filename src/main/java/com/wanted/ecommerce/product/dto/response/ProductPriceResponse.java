package com.wanted.ecommerce.product.dto.response;

import lombok.Builder;

@Builder
public record ProductPriceResponse(
    Integer basePrice,
    Integer salePrice,
    String currency,
    Double taxRate,
    Double discountPercentage
) {

    public static ProductPriceResponse of(Double basePrice, Double salePrice,
        String currency, Double taxRate, Double discountPercentage){
        return ProductPriceResponse.builder()
            .basePrice(basePrice.intValue())
            .salePrice(salePrice.intValue())
            .currency(currency)
            .taxRate(taxRate)
            .discountPercentage(discountPercentage)
            .build();
    }
}
