package com.example.demo.product.controller.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record AddProductImageRequest(
        String url,
        String altText,
        Boolean isPrimary,
        Integer displayOrder,
        Long optionId
) {
}
