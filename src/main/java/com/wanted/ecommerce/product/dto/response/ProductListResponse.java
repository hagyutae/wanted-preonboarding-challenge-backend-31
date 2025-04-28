package com.wanted.ecommerce.product.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wanted.ecommerce.brand.dto.response.BrandResponse;
import com.wanted.ecommerce.seller.dto.response.SellerResponse;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record ProductListResponse(
    Long id,
    String name,
    String slug,
    String shortDescription,
    BigDecimal basePrice,
    BigDecimal salePrice,
    String currency,
    ProductImageResponse primaryImage,
    BrandResponse brand,
    SellerResponse seller,
    Double rating,
    Integer reviewCount,
    Boolean inStock,
    String status,
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    LocalDateTime createdAt
) {

    public static ProductListResponse of(Long id, String name, String shortDescription,
        BigDecimal basePrice, BigDecimal salePrice, String currency,
        ProductImageResponse primaryImageResponse, BrandResponse brandResponse,
        SellerResponse sellerResponse, Double rating, Integer reviewCount, Boolean inStock,
        String status, LocalDateTime createdAt) {
        return ProductListResponse.builder()
            .id(id)
            .name(name)
            .shortDescription(shortDescription)
            .basePrice(basePrice)
            .salePrice(salePrice)
            .currency(currency)
            .primaryImage(primaryImageResponse)
            .brand(brandResponse)
            .seller(sellerResponse)
            .rating(rating)
            .reviewCount(reviewCount)
            .inStock(inStock)
            .status(status)
            .createdAt(createdAt)
            .build();
    }
}
