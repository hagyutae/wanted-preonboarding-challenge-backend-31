package com.psh10066.commerce.api.dto.request;

import java.math.BigDecimal;

public record CreateProductOptionRequest(
    Long optionGroupId,
    String name,
    BigDecimal additionalPrice,
    String sku,
    Integer stock,
    Integer displayOrder
) {
}
