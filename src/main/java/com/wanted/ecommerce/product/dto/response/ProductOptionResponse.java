package com.wanted.ecommerce.product.dto.response;

import com.wanted.ecommerce.product.domain.ProductOption;
import com.wanted.ecommerce.product.domain.ProductOptionGroup;
import java.util.Optional;
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

    public static ProductOptionResponse of(ProductOption option) {
        return ProductOptionResponse.builder()
            .id(option.getId())
            .optionGroupId(Optional.ofNullable(option.getOptionGroup())
                .map(ProductOptionGroup::getId)
                .orElse(null))
            .name(option.getName())
            .additionalPrice(option.getAdditionalPrice().doubleValue())
            .sku(option.getSku())
            .stock(option.getStock())
            .displayOrder(option.getDisplayOrder())
            .build();
    }
}
