package com.ecommerce.products.controller.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ProductImageRequest(
    String url,
    String altText,
    boolean isPrimary,
    Integer displayOrder,
    Long optionId
) {}