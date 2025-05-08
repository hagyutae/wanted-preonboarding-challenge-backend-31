package com.example.demo.category.dto;

import com.example.demo.category.entity.CategoryEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.Objects;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record CategorySummary(
        Long id,
        String name,
        String description,
        Integer level,
        String imageUrl,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        CategoryParentSummary parent
) {
    public static CategorySummary of(CategoryEntity categoryEntity) {
        return new CategorySummary(
                categoryEntity.getId(),
                categoryEntity.getName(),
                categoryEntity.getDescription(),
                categoryEntity.getLevel(),
                categoryEntity.getImageUrl(),
                Objects.isNull(categoryEntity.getParent().getId()) ? null : CategoryParentSummary.of(categoryEntity.getParent())
        );
    }

    public record CategoryParentSummary(
            Long id,
            String name,
            String slug
    ) {
        public static CategoryParentSummary of(CategoryEntity categoryEntity) {
            return new CategoryParentSummary(
                    categoryEntity.getId(),
                    categoryEntity.getName(),
                    categoryEntity.getSlug()
            );
        }
    }
}