package com.wanted.ecommerce.category.dto.response;

import com.wanted.ecommerce.category.domain.Category;
import com.wanted.ecommerce.product.domain.ProductCategory;
import lombok.Builder;

@Builder
public record CategoryResponse(
    Long id,
    String name,
    String slug,
    Boolean isPrimary,
    ParentCategoryResponse parent

) {

    public static CategoryResponse of(Category category, ProductCategory productCategory,
        ParentCategoryResponse parent) {
        return CategoryResponse.builder()
            .id(category.getId())
            .name(category.getName())
            .slug(category.getSlug())
            .isPrimary(productCategory.isPrimary())
            .parent(parent)
            .build();
    }
}
