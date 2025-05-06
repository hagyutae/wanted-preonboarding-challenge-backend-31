package com.sandro.wanted_shop.product.dto;

import java.math.BigDecimal;

public record UpdateOptionCommand(
        String name,
        BigDecimal additionalPrice,
        String sku,
        Integer stock,
        Integer displayOrder
) {
}
