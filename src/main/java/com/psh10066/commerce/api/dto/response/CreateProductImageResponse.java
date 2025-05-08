package com.psh10066.commerce.api.dto.response;

public record CreateProductImageResponse(
    Long id,
    String url,
    String altText,
    Boolean isPrimary,
    Integer displayOrder,
    Long optionId
) {
}
