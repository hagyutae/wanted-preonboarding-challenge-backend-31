package com.wanted.ecommerce.product.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wanted.ecommerce.brand.dto.response.BrandResponse;
import com.wanted.ecommerce.product.domain.Product;
import com.wanted.ecommerce.product.domain.ProductPrice;
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

    public static ProductListResponse of(Product product, ProductPrice price,
        ProductImageResponse image, BrandResponse brand, SellerResponse seller, double rating,
        int reviewCount, boolean inStock) {
        return ProductListResponse.builder()
            .id(product.getId())
            .name(product.getName())
            .shortDescription(product.getShortDescription())
            .basePrice(price.getBasePrice())
            .salePrice(price.getSalePrice())
            .currency(price.getCurrency())
            .primaryImage(image)
            .brand(brand)
            .seller(seller)
            .rating(rating)
            .reviewCount(reviewCount)
            .inStock(inStock)
            .status(product.getStatus().getName())
            .createdAt(product.getCreatedAt())
            .build();
    }
}
