package com.ecommerce.products.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

public class MainPageDto {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class MainPage {
        private List<ProductDto.ProductSummary> newProducts;
        private List<ProductDto.ProductSummary> popularProducts;
        private List<FeaturedCategory> featuredCategories;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class FeaturedCategory {
        private Long id;
        private String name;
        private String slug;
        private String imageUrl;
        private Integer productCount;
    }
}