package com.example.preonboarding.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ProductsDTO {
    private Long id;
    private String name;
    private String slug;
    private String shortDescription;
    private Integer basePrice;
    private Integer salesPrice;
    private String currency;
    private Long brandId;
    private String brandName;
    private Long sellerId;
    private String sellerName;
    private String status;
    private LocalDateTime createdAt;

    private ProductImageDTO images;
    private ProductOptionDTO options;
    private ReviewDTO reviews;

    @QueryProjection
    public ProductsDTO(Long id, String name, String slug, String shortDescription, Integer basePrice, Integer salesPrice, String currency, String imageUrl, String imageAltText, Long brandId, String brandName, Long sellerId, String sellerName, Integer rating, Long reviewCount, Integer stock, String status,LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.slug = slug;
        this.shortDescription = shortDescription;
        this.basePrice = basePrice;
        this.salesPrice = salesPrice;
        this.currency = currency;
        this.brandId = brandId;
        this.brandName = brandName;
        this.sellerId = sellerId;
        this.sellerName = sellerName;
        this.status = status;
        this.createdAt = createdAt;
    }

    @QueryProjection
    public ProductsDTO(Long id, String name, String slug, String shortDescription, Integer basePrice, Integer salesPrice, String currency, Long brandId, String brandName, Long sellerId, String sellerName, String status, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.slug = slug;
        this.shortDescription = shortDescription;
        this.basePrice = basePrice;
        this.salesPrice = salesPrice;
        this.currency = currency;
        this.brandId = brandId;
        this.brandName = brandName;
        this.sellerId = sellerId;
        this.sellerName = sellerName;
        this.status = status;
        this.createdAt = createdAt;
    }
}
