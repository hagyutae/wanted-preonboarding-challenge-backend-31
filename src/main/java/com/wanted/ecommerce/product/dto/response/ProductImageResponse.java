package com.wanted.ecommerce.product.dto.response;

import com.wanted.ecommerce.product.domain.ProductImage;
import lombok.Builder;

@Builder
public record ProductImageResponse(
    String url,
    String altText
) {
    public static ProductImageResponse of(ProductImage image){
        return ProductImageResponse.builder()
            .url(image.getUrl())
            .altText(image.getAltText())
            .build();
    }
}
