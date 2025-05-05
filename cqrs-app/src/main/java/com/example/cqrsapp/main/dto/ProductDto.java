package com.example.cqrsapp.main.dto;

import com.example.cqrsapp.product.domain.ProductStatus;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
public class ProductDto {

    private final Long id;
    private final String name;
    private final String slug;
    private final String shortDescription;
    private final BigDecimal basePrice;
    private final BigDecimal salePrice;
    private final String currency;
    private final String imageUrl;
    private final String imageAltText;
    private final Long brandId;
    private final String brandName;
    private final Long sellerId;
    private final String sellerName;
    private final Double rating;
    private final Long reviewCount;
    private final Boolean inStock;
    private final ProductStatus status;
    private final LocalDateTime createdAt;

    @QueryProjection
    public ProductDto(Long id, String name, String slug, String shortDescription,
                      BigDecimal basePrice, BigDecimal salePrice, String currency,
                      String imageUrl, String imageAltText,
                      Long brandId, String brandName,
                      Long sellerId, String sellerName,
                      Double rating, Long reviewCount, Boolean inStock,
                      ProductStatus status, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.slug = slug;
        this.shortDescription = shortDescription;
        this.basePrice = basePrice;
        this.salePrice = salePrice;
        this.currency = currency;
        this.imageUrl = imageUrl;
        this.imageAltText = imageAltText;
        this.brandId = brandId;
        this.brandName = brandName;
        this.sellerId = sellerId;
        this.sellerName = sellerName;
        this.rating = rating;
        this.reviewCount = reviewCount;
        this.inStock = inStock;
        this.status = status;
        this.createdAt = createdAt;
    }
}
