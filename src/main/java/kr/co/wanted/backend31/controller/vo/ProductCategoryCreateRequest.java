package kr.co.wanted.backend31.controller.vo;

import jakarta.validation.constraints.PositiveOrZero;
import kr.co.wanted.backend31.common.model.product.specification.ProductCategoryCreateSpecification;

public record ProductCategoryCreateRequest(@PositiveOrZero long categoryId, boolean isPrimary) {

    public ProductCategoryCreateSpecification to() {
        return ProductCategoryCreateSpecification.builder()
                .categoryId(categoryId)
                .isPrimary(isPrimary)
                .build();
    }

}
