package com.example.wanted_preonboarding_challenge_backend_31.shared.dto.product;

import com.example.wanted_preonboarding_challenge_backend_31.domain.model.product.ProductOptionGroup;
import java.util.List;

public record ProductOptionGroupDetailDto(
        Long id,
        String name,
        int displayOrder,
        List<ProductOptionDetailDto> options
) {

    public static ProductOptionGroupDetailDto from(ProductOptionGroup productOptionGroup,
                                                   List<ProductOptionDetailDto> options) {
        return new ProductOptionGroupDetailDto(
                productOptionGroup.getId(),
                productOptionGroup.getName(),
                productOptionGroup.getDisplayOrder(),
                options
        );
    }
}
