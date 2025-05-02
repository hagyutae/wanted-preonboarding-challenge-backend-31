package com.wanted.ecommerce.product.dto.response;

import lombok.Builder;

@Builder
public record ProductImageCreateResponse(
    Long id,
    String url,
    String altText,
    Boolean isPrimary,
    Integer displayOrder,
    Long optionId
) {

    public static ProductImageCreateResponse of(Long id, String url, String altText,
        Boolean isPrimary, Integer displayOrder, Long optionId){
        return ProductImageCreateResponse.builder()
            .id(id)
            .url(url)
            .altText(altText)
            .isPrimary(isPrimary)
            .displayOrder(displayOrder)
            .optionId(optionId)
            .build();
    }
}
