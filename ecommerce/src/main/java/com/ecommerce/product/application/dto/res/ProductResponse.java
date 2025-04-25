package com.ecommerce.product.application.dto.res;

import com.ecommerce.product.domain.Product;
import com.ecommerce.product.domain.ProductImage;
import com.ecommerce.product.domain.ProductPrice;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductResponse {
    private Long id;
    private String name;
    private String slug;
    private String short_description;
    private BigDecimal base_price;
    private BigDecimal sale_price;
    private String currency;
    private ImageResponse primary_image;
    private BrandResponse brand;
    private SellerResponse seller;
    private Double rating;
    private Integer review_count;
    private Boolean in_stock;
    private String status;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private LocalDateTime created_at;

    @Getter
    @Builder
    public static class ImageResponse {
        private String url;
        private String alt_text;
    }

    @Getter
    @Builder
    public static class BrandResponse {
        private Long id;
        private String name;
    }

    @Getter
    @Builder
    public static class SellerResponse {
        private Long id;
        private String name;
    }

    public static ProductResponse from(
            Product product,
            ProductPrice price,
            ProductImage primaryImage,
            Double rating,
            Integer reviewCount,
            Boolean inStock) {

        ImageResponse imageResponse = null;
        if (primaryImage != null) {
            imageResponse = ImageResponse.builder()
                    .url(primaryImage.getUrl())
                    .alt_text(primaryImage.getAltText())
                    .build();
        }

        BrandResponse brandResponse = null;
        if (product.getBrand() != null) {
            brandResponse = BrandResponse.builder()
                    .id(product.getBrand().getId())
                    .name(product.getBrand().getName())
                    .build();
        }

        SellerResponse sellerResponse = null;
        if (product.getSeller() != null) {
            sellerResponse = SellerResponse.builder()
                    .id(product.getSeller().getId())
                    .name(product.getSeller().getName())
                    .build();
        }

        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .slug(product.getSlug())
                .short_description(product.getShortDescription())
                .base_price(price != null ? price.getBasePrice() : null)
                .sale_price(price != null ? price.getSalePrice() : null)
                .currency(price != null ? price.getCurrency() : "KRW")
                .primary_image(imageResponse)
                .brand(brandResponse)
                .seller(sellerResponse)
                .rating(rating)
                .review_count(reviewCount)
                .in_stock(inStock)
                .status(product.getStatus() != null ? product.getStatus().name() : null)
                .created_at(product.getCreatedAt())
                .build();
    }
}
