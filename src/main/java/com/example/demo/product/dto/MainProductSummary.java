package com.example.demo.product.dto;

import com.example.demo.brand.entity.BrandEntity;
import com.example.demo.product.entity.ProductEntity;
import com.example.demo.product.entity.ProductImageEntity;
import com.example.demo.productoption.dto.ProductStock;
import com.example.demo.review.dto.ReviewStatistic;
import com.example.demo.user.entity.SellerEntity;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record MainProductSummary(
        List<ProductSummary> newProducts,
        List<ProductSummary> popularProducts,
        List<FeaturedCategorySummary> featuredCategories
) {
    public static MainProductSummary of(
            List<ProductEntity> latestProductEntityList,
            List<ProductEntity> hotProductEntityList,
            List<FeaturedCategory> featuredCategories,
            Map<Long, ProductImageEntity> productPrimaryImageMap,
            Map<Long, ReviewStatistic> reviewStatisticMap,
            Map<Long, ProductStock> productStockMap
    ) {
        return new MainProductSummary(
                latestProductEntityList.stream().map(it -> ProductSummary.of(
                        it,
                        productPrimaryImageMap.get(it.getId()),
                        reviewStatisticMap.get(it.getId()),
                        productStockMap.get(it.getId())
                )).toList(),
                hotProductEntityList.stream().map(it -> ProductSummary.of(
                        it,
                        productPrimaryImageMap.get(it.getId()),
                        reviewStatisticMap.get(it.getId()),
                        productStockMap.get(it.getId())
                )).toList(),
                featuredCategories.stream().map(FeaturedCategorySummary::of)
                        .toList()
        );
    }

    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public record ProductSummary(
            Long id,
            String name,
            String slug,
            String shortDescription,
            BigDecimal basePrice,
            BigDecimal salePrice,
            String currency,
            ProductImage primaryImage,
            BrandSummary brand,
            SellerSummary seller,
            Double rating,
            Long reviewCount,
            Boolean inStock,
            String status,
            LocalDateTime createdAt
    ) {
        public static ProductSummary of(ProductEntity productEntity, ProductImageEntity productImageEntity, ReviewStatistic reviewStatistic, ProductStock productStock) {
            return new ProductSummary(
                    productEntity.getId(),
                    productEntity.getName(),
                    productEntity.getSlug(),
                    productEntity.getShortDescription(),
                    productEntity.getProductPriceEntity().getBasePrice(),
                    productEntity.getProductPriceEntity().getSalePrice(),
                    productEntity.getProductPriceEntity().getCurrency(),
                    Objects.isNull(productImageEntity) ? new ProductImage(null, null) : ProductImage.of(productImageEntity),
                    BrandSummary.of(productEntity.getBrandEntity()),
                    SellerSummary.of(productEntity.getSellerEntity()),
                    Objects.nonNull(reviewStatistic) ? reviewStatistic.averageRating() : 0L,
                    Objects.nonNull(reviewStatistic) ? reviewStatistic.reviewCount() : 0L,
                    Objects.nonNull(productStock) && productStock.stockCount() > 0,
                    productEntity.getStatus().name(),
                    productEntity.getCreatedAt()
            );
        }

        @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
        public record ProductImage(
                String url,
                String altText
        ) {
            public static ProductImage of(ProductImageEntity productImageEntity) {
                return new ProductImage(
                        productImageEntity.getUrl(),
                        productImageEntity.getAltText()
                );
            }
        }

        @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
        public record BrandSummary(
                Long id,
                String name
        ) {
            public static BrandSummary of(BrandEntity brandEntity) {
                return new BrandSummary(
                        brandEntity.getId(),
                        brandEntity.getName()
                );
            }
        }

        @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
        public record SellerSummary(
                Long id,
                String name
        ) {
            public static SellerSummary of(SellerEntity sellerEntity) {
                return new SellerSummary(
                        sellerEntity.getId(),
                        sellerEntity.getName()
                );
            }
        }
    }

    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public record FeaturedCategorySummary(
            Long id,
            String name,
            String slug,
            String imageUrl,
            Long productCount
    ) {
        public static FeaturedCategorySummary of(FeaturedCategory featuredCategory) {
            return new FeaturedCategorySummary(
                    featuredCategory.id(),
                    featuredCategory.name(),
                    featuredCategory.slug(),
                    featuredCategory.imageUrl(),
                    featuredCategory.productCount()
            );
        }
    }
}
