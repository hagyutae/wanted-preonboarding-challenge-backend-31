package com.example.demo.product.dto;

import com.example.demo.product.entity.ProductEntity;
import com.example.demo.product.entity.ProductImageEntity;
import com.example.demo.productoption.dto.ProductStock;
import com.example.demo.review.dto.ReviewStatistic;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.Objects;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record ProductSummary(
        Long id,
        String name,
        String slug,
        String shortDescription,
        BigDecimal basePrice,
        BigDecimal salePrice,
        String currency,
        ProductImageSummary primaryImage,
        BrandSummary brand,
        SellerSummary seller,
        Double rating,
        Long reviewCount,
        Boolean inStock,
        String status,
        LocalDateTime createdAt
) {
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    private record ProductImageSummary(
            String url,
            String altText
    ) {
    }

    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    private record BrandSummary(
            Long id,
            String name
    ) {
    }

    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    private record SellerSummary(
            Long id,
            String name
    ) {
    }

    public static ProductSummary of(ProductEntity productEntity,
                                    ProductImageEntity productImageEntity,
                                    ReviewStatistic reviewStatistic,
                                    ProductStock productStock) {
        return new ProductSummary(
                productEntity.getId(),
                productEntity.getName(),
                productEntity.getSlug(),
                productEntity.getShortDescription(),
                productEntity.getProductPriceEntity().getBasePrice(),
                productEntity.getProductPriceEntity().getSalePrice(),
                productEntity.getProductPriceEntity().getCurrency(),
                Objects.nonNull(productImageEntity) ? new ProductImageSummary(productImageEntity.getUrl(), productImageEntity.getAltText())
                        : new ProductImageSummary(null, null),
                new BrandSummary(
                        productEntity.getBrandEntity().getId(),
                        productEntity.getBrandEntity().getName()
                ),
                new SellerSummary(
                        productEntity.getSellerEntity().getId(),
                        productEntity.getSellerEntity().getName()
                ),
                Objects.nonNull(reviewStatistic) ?
                        BigDecimal.valueOf(reviewStatistic.averageRating())
                                .setScale(2, RoundingMode.HALF_UP)
                                .doubleValue()
                        : 0.0,
                Objects.nonNull(reviewStatistic) ? reviewStatistic.reviewCount() : 0L,
                Objects.nonNull(productStock) && productStock.stockCount() > 0,
                productEntity.getStatus().name(),
                productEntity.getCreatedAt()
        );
    }
}