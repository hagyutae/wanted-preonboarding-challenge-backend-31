package com.sandro.wanted_shop.product.dto;

import com.sandro.wanted_shop.product.entity.enums.ProductStatus;

public record UpdateProductCommand(
        String name,
        String slug,
        String shortDescription,
        String fullDescription,
        ProductStatus status
) {
}
