package com.psh10066.commerce.api.dto.response;

import com.psh10066.commerce.domain.model.product.ProductStatus;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder(toBuilder = true)
public record GetAllProductsResponse(
    Long id,
    String name,
    String slug,
    String shortDescription,
    BigDecimal basePrice,
    BigDecimal salePrice,
    String currency,

    PrimaryImage primaryImage,
    Brand brand,
    Seller seller,

    BigDecimal rating,
    Integer reviewCount,
    Boolean inStock,
    ProductStatus status,
    LocalDateTime createdAt
) {

    public record PrimaryImage(
        String url,
        String altText
    ) {
    }

    public record Brand(
        Long id,
        String name
    ) {
    }

    public record Seller(
        Long id,
        String name
    ) {
    }
}
