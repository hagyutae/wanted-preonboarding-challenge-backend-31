package com.wanted.ecommerce.common.dto.response;

import com.wanted.ecommerce.brand.domain.Brand;
import com.wanted.ecommerce.product.domain.Product;
import com.wanted.ecommerce.product.domain.ProductImage;
import com.wanted.ecommerce.product.domain.ProductStatus;
import com.wanted.ecommerce.review.domain.Review;
import com.wanted.ecommerce.seller.domain.Seller;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductItemResponse {

    private Long id;
    private String name;
    private String slug;
    private String shortDescription;
    private BigDecimal basePrice;
    private BigDecimal salePrice;
    private String currency;
    private ProductImageResponse primaryImage;
    private BrandResponse brand;
    private SellerResponse seller;
    private Double rating;
    private Integer reviewCount;
    private Boolean inStock;
    private ProductStatus status;
    private LocalDateTime createdAt;

    public static ProductItemResponse of(Product product) {
        return ProductItemResponse.builder()
            .id(product.getId())
            .name(product.getName())
            .slug(product.getSlug())
            .shortDescription(product.getShortDescription())
            .basePrice(product.getPrice().getBasePrice())
            .salePrice(product.getPrice().getSalePrice())
            .currency(product.getPrice().getCurrency())
            .primaryImage(product.getImages().stream()
                .filter(ProductImage::isPrimary)
                .findFirst()
                .map(ProductImageResponse::of)
                .orElse(null))
            .brand(BrandResponse.of(product.getBrand()))
            .seller(SellerResponse.of(product.getSeller()))
            .rating(product.getReviews().stream()
                .mapToInt(Review::getRating)
                .average()
                .orElse(0.0))
            .reviewCount(product.getReviews().size())
            .inStock(product.getStatus().equals(ProductStatus.ACTIVE))
            .status(product.getStatus())
            .createdAt(product.getCreatedAt())
            .build();
    }

    @Builder
    public record ProductImageResponse(String url, String altText) {

        public static ProductImageResponse of(ProductImage image) {
            return new ProductImageResponse(image.getUrl(), image.getAltText());
        }
    }

    @Builder
    public record BrandResponse(Long id, String name) {

        public static BrandResponse of(Brand brand) {
            return new BrandResponse(brand.getId(), brand.getName());
        }
    }

    @Builder
    public record SellerResponse(Long id, String name) {

        public static SellerResponse of(Seller seller) {
            return new SellerResponse(seller.getId(), seller.getName());
        }
    }
}
