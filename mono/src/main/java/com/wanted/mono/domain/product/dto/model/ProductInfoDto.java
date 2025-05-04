package com.wanted.mono.domain.product.dto.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wanted.mono.domain.category.entity.dto.ProductCategoryDto;
import com.wanted.mono.domain.product.dto.ProductRelatedDto;
import com.wanted.mono.domain.product.dto.RatingDto;
import com.wanted.mono.domain.tag.entity.dto.TagDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductInfoDto {
    private Long id;
    private String name;
    private String slug;
    @JsonProperty("short_description")
    private String shortDescription;

    @JsonProperty("full_description")
    private String fullDescription;

    private SellerDto seller;
    private BrandDto brand;
    private String status;
    @JsonProperty("created_at")
    private LocalDateTime createdAt;
    
    @JsonProperty("updated_at")
    private LocalDateTime updatedAt;
    private ProductDetailDto detail;
    private ProductPriceDto price;
    private List<ProductCategoryDto> categories;
    @JsonProperty("option_groups")
    private List<ProductOptionGroupDto> optionGroups;
    private List<ProductImageDto> images;
    private List<TagDto> tags;
    private RatingDto rating;
    @JsonProperty("related_products")
    private List<ProductRelatedDto> relatedProducts;

    public ProductInfoDto(Long id, String name, String slug, String shortDescription, String fullDescription, SellerDto seller, BrandDto brand, String status, LocalDateTime createdAt, LocalDateTime updatedAt, ProductDetailDto detail, ProductPriceDto price) {
        this.id = id;
        this.name = name;
        this.slug = slug;
        this.shortDescription = shortDescription;
        this.fullDescription = fullDescription;
        this.seller = seller;
        this.brand = brand;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.detail = detail;
        this.price = price;
    }
}
