package com.example.demo.category.dto;

import com.example.demo.category.entity.CategoryEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.ArrayList;
import java.util.List;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record CategoryTree(
        Long id,
        String name,
        String slug,
        String description,
        int level,
        String imageUrl,
        @JsonIgnore
        Long parentId,
        @JsonInclude(JsonInclude.Include.NON_EMPTY)
        List<CategoryTree> children
) {
    public static CategoryTree of(CategoryEntity categoryEntity) {
        return new CategoryTree(
                categoryEntity.getId(),
                categoryEntity.getName(),
                categoryEntity.getSlug(),
                categoryEntity.getDescription(),
                categoryEntity.getLevel(),
                categoryEntity.getImageUrl(),
                categoryEntity.getParent() != null ? categoryEntity.getParent().getId() : null,
                new ArrayList<>()
        );
    }
}

