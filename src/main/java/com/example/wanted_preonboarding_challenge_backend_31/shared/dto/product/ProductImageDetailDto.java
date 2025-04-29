package com.example.wanted_preonboarding_challenge_backend_31.shared.dto.product;

import com.example.wanted_preonboarding_challenge_backend_31.domain.model.product.ProductImage;

public record ProductImageDetailDto(
        Long id,
        String url,
        String altText,
        boolean isPrimary,
        int displayOrder,
        Long optionId
) {

    public static ProductImageDetailDto from(ProductImage image) {
        return new ProductImageDetailDto(
                image.getId(),
                image.getUrl(),
                image.getAltText(),
                image.isPrimary(),
                image.getDisplayOrder(),
                image.getProductOption() == null ? null : image.getProductOption().getId()
        );
    }
}
