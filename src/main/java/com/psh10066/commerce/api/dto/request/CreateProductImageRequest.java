package com.psh10066.commerce.api.dto.request;

public record CreateProductImageRequest(
    String url,
    String altText,
    Boolean isPrimary,
    Integer displayOrder,
    Long optionId
) {
}
