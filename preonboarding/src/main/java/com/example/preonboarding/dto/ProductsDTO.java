package com.example.preonboarding.dto;

import com.example.preonboarding.domain.Products;
import com.fasterxml.jackson.annotation.JsonInclude;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductsDTO {
    private Long id;
    private String name;
    private String slug;
    private String shortDescription;
    private String fullDescription;
    private Integer basePrice;
    private Integer salesPrice;
    private String currency;
    private Long brandId;
    private String brandName;
    private Long sellerId;
    private String sellerName;
    private String sellerDescription;
    private String sellerLogoUrl;
    private Double sellerRating;
    private String sellerContactEmail;
    private String sellerContactPhone;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private ProductImageDTO images;
    private ProductOptionDTO options;
    private ReviewDTO reviews;

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

    @QueryProjection
    public ProductsDTO(Long id, String name, String slug, String shortDescription, String fullDescription, Long sellerId, String sellerName, String sellerDescription, String sellerLogoUrl, Double sellerRating, String sellerContactEmail, String sellerContactPhone, Long brandId, String brandName, String status, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.slug = slug;
        this.shortDescription = shortDescription;
        this.fullDescription = fullDescription;
        this.sellerId = sellerId;
        this.sellerName = sellerName;
        this.sellerDescription = sellerDescription;
        this.sellerLogoUrl = sellerLogoUrl;
        this.sellerRating = sellerRating;
        this.sellerContactEmail = sellerContactEmail;
        this.sellerContactPhone = sellerContactPhone;
        this.brandId = brandId;
        this.brandName = brandName;
        this.status = status;
        this.createdAt = createdAt;
    }

    public ProductsDTO(Products products) {
        this.id = products.getId();
        this.name = products.getName();
        this.slug = products.getSlug();
        this.createdAt = products.getCreatedAt();
        this.updatedAt = products.getUpdatedAt();
    }
}
