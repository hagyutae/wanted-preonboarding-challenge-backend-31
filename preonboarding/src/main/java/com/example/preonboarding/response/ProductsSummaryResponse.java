package com.example.preonboarding.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ProductsSummaryResponse {

    private Long id;
    private String name;
    private String slug;
    private String shortDescription;
    private Integer basePrice;
    private Integer salesPrice;
    private String currency;
    private ImageResponse primaryImage;
    private BrandResponse brand;
    private SellerResponse seller;
    private Double rating;
    private Long reviewCount;
    private boolean inStock;
    private String status;
    private LocalDateTime createdAt;


    public ProductsSummaryResponse(Long id, String name, String slug, String shortDescription, Integer basePrice, Integer salesPrice, String currency, ImageResponse primaryImage, BrandResponse brand, SellerResponse seller, Double rating, Long reviewCount, boolean stock, String status, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.slug = slug;
        this.shortDescription = shortDescription;
        this.basePrice = basePrice;
        this.salesPrice = salesPrice;
        this.currency = currency;
        this.primaryImage = primaryImage;
        this.brand = brand;
        this.seller = seller;
        this.rating = rating;
        this.reviewCount = reviewCount;
        this.inStock = stock;
        this.status = status;
        this.createdAt = createdAt;
    }

    public ProductsSummaryResponse(Long id, String name, String slug, String shortDescription, Integer basePrice, Integer salesPrice, String currency, ImageResponse primaryImage, BrandResponse brand) {
        this.id = id;
        this.name = name;
        this.slug = slug;
        this.shortDescription = shortDescription;
        this.basePrice = basePrice;
        this.salesPrice = salesPrice;
        this.currency = currency;
        this.primaryImage = primaryImage;
        this.brand = brand;
    }
}
