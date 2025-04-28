package com.wanted.ecommerce.product.dto.response;

import lombok.Builder;

@Builder
public record ProductDetailImageResponse(
    Long id,
    String url,
    String altText,
    Boolean isPrimary,
    Integer displayOrder,
    Long optionId
) {
    public static ProductDetailImageResponse of(Long id, String url, String altText, Boolean isPrimary, Integer displayOrder, Long optionId){
        return ProductDetailImageResponse.builder()
            .id(id)
            .url(url)
            .altText(altText)
            .isPrimary(isPrimary)
            .displayOrder(displayOrder)
            .optionId(optionId)
            .build();
    }
}
