package com.ecommerce.products.controller.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ProductCreateRequest(
    String name,
    String slug,
    String shortDescription,
    String fullDescription,
    Long sellerId,
    Long brandId,
    String status, // ACTIVE, OUT_OF_STOCK, DELETED

    ProductDetailDto detail,
    ProductPriceDto price,

    List<ProductCategoryDto> categories,

    List<OptionGroupDto> optionGroups,

    List<ImageDto> images,

    List<Long> tagIds
) {

    @Builder
    public ProductCreateRequest {
        if (categories == null) {
            categories = new ArrayList<>();
        }
        if (optionGroups == null) {
            optionGroups = new ArrayList<>();
        }
        if (images == null) {
            images = new ArrayList<>();
        }
        if (tagIds == null) {
            tagIds = new ArrayList<>();
        }
    }

    @Builder
    @JsonIgnoreProperties(ignoreUnknown = true)
    public record ProductDetailDto(
        BigDecimal weight,
        Map<String, Object> dimensions,
        String materials,
        String countryOfOrigin,
        String warrantyInfo,
        String careInstructions,
        Map<String, Object> additionalInfo
    ) {}

    @Builder
    @JsonIgnoreProperties(ignoreUnknown = true)
    public record ProductPriceDto(
        BigDecimal basePrice,
        BigDecimal salePrice,
        BigDecimal costPrice,
        String currency,
        BigDecimal taxRate
    ) {}

    @Builder
    @JsonIgnoreProperties(ignoreUnknown = true)
    public record ProductCategoryDto(
        Long categoryId,
        Boolean isPrimary
    ) {
        public ProductCategoryDto {
            if (isPrimary == null) {
                isPrimary = false;
            }
        }
    }

    @Builder
    @JsonIgnoreProperties(ignoreUnknown = true)
    public record OptionGroupDto(
        String name,
        Integer displayOrder,
        List<OptionDto> options
    ){
        @Builder
        public OptionGroupDto {
            if (options == null) {
                options = new ArrayList<>();
            }
        }
    }

    @Builder
    @JsonIgnoreProperties(ignoreUnknown = true)
    public record OptionDto(
        String name,
        BigDecimal additionalPrice,
        String sku,
        Integer stock,
        Integer displayOrder
    ) { }

    
    @Builder
    @JsonIgnoreProperties(ignoreUnknown = true)
    public record ImageDto(
        String url,
        String altText,
        boolean isPrimary,
        Integer displayOrder,
        Long optionId
    ) { }
}



