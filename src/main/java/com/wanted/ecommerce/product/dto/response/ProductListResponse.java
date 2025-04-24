package com.wanted.ecommerce.product.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ProductListResponse(
    Long id,
    String name,
    String slug,
    String shortDescription,
    BigDecimal basePrice,
    BigDecimal salePrice,
    String currency,
//    ProductImageResponse primaryImage,
//    BrandResponse brand,
//    SellerResponse seller,
    BigDecimal rating,
    int reviewCount,
    boolean inStock,
    String status,
    LocalDateTime createdAt
) {
}
