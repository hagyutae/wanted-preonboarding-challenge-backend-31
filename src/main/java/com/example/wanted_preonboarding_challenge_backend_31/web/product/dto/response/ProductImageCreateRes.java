package com.example.wanted_preonboarding_challenge_backend_31.web.product.dto.response;

import com.example.wanted_preonboarding_challenge_backend_31.domain.model.product.ProductImage;

public record ProductImageCreateRes(
        Long id,
        String url,
        String altText,
        boolean isPrimary,
        int displayOrder,
        Long optionId
) {

    public static ProductImageCreateRes from(ProductImage entity, Long optionId) {
        return new ProductImageCreateRes(entity.getId(), entity.getUrl(), entity.getAltText(),
                entity.isPrimary(), entity.getDisplayOrder(), optionId);
    }
}
