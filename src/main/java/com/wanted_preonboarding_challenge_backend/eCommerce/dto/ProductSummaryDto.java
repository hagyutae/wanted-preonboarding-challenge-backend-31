package com.wanted_preonboarding_challenge_backend.eCommerce.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wanted_preonboarding_challenge_backend.eCommerce.dto.Image.ImageDto;
import com.wanted_preonboarding_challenge_backend.eCommerce.dto.brand.BrandDto;
import com.wanted_preonboarding_challenge_backend.eCommerce.dto.seller.SellerDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductSummaryDto {
    private Long id;
    private String name;
    private String slug;
    @JsonProperty("short_description")
    private String shortDescription;

    @JsonProperty("base_price")
    private Integer basePrice;

    @JsonProperty("sale_price")
    private Integer salePrice;

    private String currency;

    @JsonProperty("primary_image")
    private ImageDto primaryImage;

    private BrandDto brand;
    private SellerDto seller;

    private Double rating;

    @JsonProperty("review_count")
    private Integer reviewCount;

    @JsonProperty("in_stock")
    private Boolean inStock;

    private String status;

    @JsonProperty("created_at")
    private LocalDateTime createdAt;
}
