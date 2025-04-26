package com.example.wanted_preonboarding_challenge_backend_31.web.product.dto.response;

import com.example.wanted_preonboarding_challenge_backend_31.domain.model.product.ProductOption;
import java.math.BigDecimal;

public record ProductOptionUpdateRes(
        Long id,
        Long optionGroupId,
        String name,
        BigDecimal additionalPrice,
        String sku,
        int stock,
        int displayOrder
) {

    public static ProductOptionUpdateRes from(Long optionGroupId, ProductOption entity) {
        return new ProductOptionUpdateRes(entity.getId(), optionGroupId, entity.getName(), entity.getAdditionalPrice(),
                entity.getSku(), entity.getStock(), entity.getDisplayOrder());
    }
}
