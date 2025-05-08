package com.example.demo.product.controller.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.math.BigDecimal;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record ProductPriceRequest(
        BigDecimal basePrice,
        BigDecimal salePrice,
        BigDecimal costPrice,
        String currency,
        BigDecimal taxRate
) {}
