package com.shopping.mall.main.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class MainPageResponse {

    private List<ProductSimpleResponse> newProducts;
    private List<CategoryPopularProductsResponse> popularProductsByCategory;

    @Getter
    @Builder
    @AllArgsConstructor
    public static class ProductSimpleResponse {
        private Long id;
        private String name;
        private String slug;
        private String shortDescription;
        private Integer salePrice;
        private String imageUrl;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class CategoryPopularProductsResponse {
        private Long categoryId;
        private String categoryName;
        private List<ProductSimpleResponse> products;
    }
}
