package com.wanted.ecommerce.product.dto.response;

import lombok.Builder;

@Builder
public record ProductOptionResponse(
    Long id,
    Long optionGroupId,
    String name,
    Double additionalPrice,
    String sku,
    Integer stock,
    Integer displayOrder
) {

    public static ProductOptionResponse of(Long id, Long optionGroupId, String name,
        Double additionalPrice, String sku, Integer stock, Integer displayOrder) {
        return ProductOptionResponse.builder()
            .id(id)
            .optionGroupId(optionGroupId)
            .name(name)
            .additionalPrice(additionalPrice)
            .sku(sku)
            .stock(stock)
            .displayOrder(displayOrder)
            .build();
    }

    public static ProductOptionResponse of(Long id, String name,
        Double additionalPrice, String sku, Integer stock, Integer displayOrder) {
        return ProductOptionResponse.builder()
            .id(id)
            .name(name)
            .additionalPrice(additionalPrice)
            .sku(sku)
            .stock(stock)
            .displayOrder(displayOrder)
            .build();
    }
}
