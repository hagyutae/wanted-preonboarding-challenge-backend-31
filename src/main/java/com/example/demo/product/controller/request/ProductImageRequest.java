package com.example.demo.product.controller.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record ProductImageRequest(
        String url,
        String altText,
        boolean isPrimary,
        int displayOrder,
        Long optionId
) {}
