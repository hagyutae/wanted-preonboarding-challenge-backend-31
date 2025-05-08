package com.example.demo.product.controller.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.math.BigDecimal;
import java.util.List;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record ProductOptionGroupRequest(
        String name,
        int displayOrder,
        List<ProductOptionRequest> options
) {
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public record ProductOptionRequest(
            String name,
            BigDecimal additionalPrice,
            String sku,
            int stock,
            int displayOrder
    ) {}

}
