package com.preonboarding.dto.response.product;

import com.preonboarding.domain.*;
import com.preonboarding.dto.response.review.ReviewSummaryResponse;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductPageResponse {
    private Long id;
    private String name;
    private String slug;
    private String shortDescription;
    private String status;
    private LocalDateTime createdAt;

    private String currency;
    private BigDecimal basePrice;
    private BigDecimal salePrice;

    private ProductPrimaryImage primaryImage;

    @NonNull
    private ProductBrandResponse brand;
    @NonNull
    private ProductSellerResponse seller;

    @NonNull
    private Double rating;
    @NonNull
    private Long reviewCount;

    public static ProductPageResponse from(Product product,ReviewSummaryResponse reviewSummaryResponse) {
        ProductPrice productPrice = product.getProductPrice();

        ProductImage productImage = null;
        if (!product.getProductImageList().isEmpty()) {
            productImage = product.getProductImageList().get(0);
        }

        Brand brand = product.getBrand();
        Seller seller = product.getSeller();

        return ProductPageResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .slug(product.getSlug())
                .shortDescription(product.getShortDescription())
                .status(product.getStatus())
                .createdAt(product.getCreatedAt())
                .currency(productPrice.getCurrency())
                .basePrice(productPrice.getBasePrice())
                .salePrice(productPrice.getSalePrice())
                .primaryImage(ProductPrimaryImage.of(productImage))
                .brand(ProductBrandResponse.of(brand))
                .seller(ProductSellerResponse.of(seller))
                .rating(reviewSummaryResponse.getAverageRating())
                .reviewCount(reviewSummaryResponse.getTotalCount())
                .build();
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    private static class ProductBrandResponse {
        private Long id;
        private String name;

        public static ProductBrandResponse of(Brand brand) {
            return ProductBrandResponse.builder()
                    .id(brand.getId())
                    .name(brand.getName())
                    .build();
        }
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    private static class ProductSellerResponse {
        private Long id;
        private String name;

        public static ProductSellerResponse of(Seller seller) {
            return ProductSellerResponse.builder()
                    .id(seller.getId())
                    .name(seller.getName())
                    .build();
        }
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    private static class ProductPrimaryImage {
        private String url;
        private String altText;

        public static ProductPrimaryImage of(ProductImage image) {
            return ProductPrimaryImage.builder()
                    .url(image.getUrl())
                    .altText(image.getAltText())
                    .build();
        }
    }
}
