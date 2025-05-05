package com.example.cqrsapp.common.dto;

import com.example.cqrsapp.product.domain.ProductStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ProductSummaryItem {
    private final Long id;
    private final String name;
    private final String slug;
    private final String shortDescription;
    private final Long basePrice;
    private final Long salePrice;
    private final String currency;
    private final Double rating;
    private final Long reviewCount;
    private final Boolean inStock;
    private final ProductStatus status;
    private final LocalDateTime createdAt;
    private final ProductImageDto primaryImage;
    private final BrandSummaryDto brand;
    private final SellerSummaryDto seller;

    public ProductSummaryItem(Long id,
                              String name,
                              String slug,
                              String shortDescription,
                              BigDecimal basePrice,
                              BigDecimal salePrice,
                              String currency,
                              String imageUrl,
                              String imageAlt,
                              Long brandId,
                              String brandName,
                              Long sellerId,
                              String sellerName,
                              Double rating,
                              Long reviewCount,
                              Long stockCount,
                              ProductStatus productStatus,
                              LocalDateTime createdAt
    ) {
        this.id = id;
        this.name = name;
        this.slug = slug;
        this.shortDescription = shortDescription;
        this.basePrice = basePrice.longValue();
        this.salePrice = salePrice.longValue();
        this.primaryImage = new ProductImageDto(imageUrl, imageAlt);
        this.brand = new BrandSummaryDto(brandId, brandName);
        this.seller = new SellerSummaryDto(sellerId, sellerName);
        this.rating = rating;
        this.reviewCount = reviewCount;
        this.inStock = stockCount > 0;
        this.status = productStatus;
        this.currency = currency;
        this.createdAt = createdAt;
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    public static class ProductImageDto {
        private String url;
        private String altText;
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    public static class BrandSummaryDto {
        private Long id;
        private String name;
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    public static class SellerSummaryDto {
        private Long id;
        private String name;
    }
}
