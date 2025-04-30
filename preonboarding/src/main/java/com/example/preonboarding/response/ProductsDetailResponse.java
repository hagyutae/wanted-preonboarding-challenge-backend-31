package com.example.preonboarding.response;

import com.example.preonboarding.domain.ProductDetails;
import com.example.preonboarding.domain.ProductOptionGroup;
import com.example.preonboarding.dto.*;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ProductsDetailResponse {

    private Long id;
    private String name;
    private String slug;
    private String shortDescription;
    private String fullDescription;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private SellerResponse seller;
    private BrandResponse brand;
    private DetailDTO detail;
    private PriceDTO price;
    private List<ProductCategoriesDTO> categories;
    private List<ProductOptionDTO> optionGroups;
    private List<ProductImageDTO> images;
    private List<TagsDTO> tags;
    private RatingDTO rating;



    @Builder
    public ProductsDetailResponse(Long id,
                                  String name,
                                  String slug,
                                  String shortDescription,
                                  String fullDescription,
                                  SellerResponse seller,
                                  BrandResponse brand,
                                  String status,
                                  LocalDateTime createdAt,
                                  LocalDateTime updatedAt,
                                  DetailDTO detail,
                                  PriceDTO price,
                                  List<ProductImageDTO> images,
                                  List<ProductCategoriesDTO> categories,
                                  List<ProductOptionDTO> optionGroups,
                                  List<TagsDTO> tags,
                                  RatingDTO rating)
    {
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
        this.categories =categories;
        this.optionGroups = optionGroups;
        this.images = images;
        this.tags = tags;
        this.rating = rating;
    }
}
