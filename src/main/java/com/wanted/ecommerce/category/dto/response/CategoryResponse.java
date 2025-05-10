package com.wanted.ecommerce.category.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.wanted.ecommerce.category.domain.Category;
import com.wanted.ecommerce.product.domain.ProductCategory;
import lombok.Builder;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record CategoryResponse(
    Long id,
    String name,
    String slug,
    Boolean isPrimary,
    ParentCategoryResponse parent

) {

    public static CategoryResponse of(Category category, ProductCategory productCategory) {
        return CategoryResponse.builder()
            .id(category.getId())
            .name(category.getName())
            .slug(category.getSlug())
            .isPrimary(productCategory.isPrimary())
            .parent(category.getParent() != null ? ParentCategoryResponse.of(category) : null)
            .build();
    }

    @Builder
    public record ParentCategoryResponse(
        Long id,
        String name,
        String slug
    ) {
        public static ParentCategoryResponse of(Category category){
            return ParentCategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .slug(category.getSlug())
                .build();
        }
    }
}
