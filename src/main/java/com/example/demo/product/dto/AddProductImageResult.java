package com.example.demo.product.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record AddProductImageResult(
        Long id,
        String url,
        String altText,
        Boolean isPrimary,
        Integer displayOrder,
        Long optionId
) {
}
