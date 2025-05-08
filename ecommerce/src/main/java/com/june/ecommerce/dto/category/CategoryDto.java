package com.june.ecommerce.dto.category;

import com.june.ecommerce.domain.category.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {

    private int id;
    private String name;
    private String slug;
    private boolean isPrimary;
    private ParentCategoryDto parent;

    public static CategoryDto fromEntity(Category category, boolean isPrimary) {
        return CategoryDto.builder()
                .id(category.getId())
                .name(category.getName())
                .slug(category.getSlug())
                .isPrimary(isPrimary)
                .parent(
                        category.getParent() != null
                                ? ParentCategoryDto.fromEntity(category.getParent())
                                : null
                )
                .build();
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ParentCategoryDto {
        private int id;
        private String name;
        private String slug;

        public static ParentCategoryDto fromEntity(Category parent) {
            return ParentCategoryDto.builder()
                    .id(parent.getId())
                    .name(parent.getName())
                    .slug(parent.getSlug())
                    .build();
        }
    }
}