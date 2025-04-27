package com.wanted.ecommerce.product.dto.response;

import lombok.Builder;

@Builder
public record ProductImageResponse(
    String url,
    String altText
) {
    public static ProductImageResponse of(String url, String altText){
        return ProductImageResponse.builder()
            .url(url)
            .altText(altText)
            .build();
    }
}
