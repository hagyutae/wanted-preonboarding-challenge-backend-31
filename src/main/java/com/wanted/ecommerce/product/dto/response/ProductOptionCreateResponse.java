package com.wanted.ecommerce.product.dto.response;

import lombok.Builder;

@Builder
public record ProductOptionCreateResponse(
    Long id,
    Long optionGroupId,
    String name,
    Double additionalPrice,
    String sku,
    Integer stock,
    Integer displayOrder
) {

    public static ProductOptionCreateResponse of(Long id, Long optionGroupId, String name,
        Double additionalPrice, String sku, Integer stock, Integer displayOrder) {
        return ProductOptionCreateResponse.builder()
            .id(id)
            .optionGroupId(optionGroupId)
            .name(name)
            .additionalPrice(additionalPrice)
            .sku(sku)
            .stock(stock)
            .displayOrder(displayOrder)
            .build();
    }
}
