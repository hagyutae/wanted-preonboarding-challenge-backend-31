package com.wanted.mono.domain.category.entity.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wanted.mono.domain.category.entity.Category;
import com.wanted.mono.domain.category.entity.ProductCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ProductCategoryDto {
    private Long id;
    private String name;
    private String slug;
    @JsonProperty("is_primary")
    private boolean isPrimary;
    private Parent parent;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    static class Parent {
        private Long id;
        private String name;
        private String slug;
    }

    // -----------------------------
    public static ProductCategoryDto of(ProductCategory productCategory) {
        ProductCategoryDto productCategoryDto = new ProductCategoryDto();
        productCategoryDto.id = productCategory.getCategory().getId();
        productCategoryDto.name = productCategory.getCategory().getName();
        productCategoryDto.slug = productCategory.getCategory().getSlug();
        productCategoryDto.isPrimary = productCategory.getIsPrimary();

        Category parent = productCategory.getCategory().getParent();
        productCategoryDto.parent = parent == null
                ? null
                : new Parent(parent.getId(), parent.getName(), parent.getSlug());
        return productCategoryDto;
    }
}
