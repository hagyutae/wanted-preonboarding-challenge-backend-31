package com.example.wanted_preonboarding_challenge_backend_31.web.product.dto.response;

import com.example.wanted_preonboarding_challenge_backend_31.domain.model.product.ProductOption;
import java.math.BigDecimal;

public record ProductOptionCreateRes(
        Long id,
        Long optionGroupId,
        String name,
        BigDecimal additionalPrice,
        String sku,
        int stock,
        int displayOrder
) {

    public static ProductOptionCreateRes from(Long optionGroupId, ProductOption entity) {
        return new ProductOptionCreateRes(entity.getId(), optionGroupId, entity.getName(), entity.getAdditionalPrice(),
                entity.getSku(), entity.getStock(), entity.getDisplayOrder());
    }
}
