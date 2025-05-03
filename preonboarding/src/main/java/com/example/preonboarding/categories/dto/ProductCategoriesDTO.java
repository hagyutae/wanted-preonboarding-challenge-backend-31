package com.example.preonboarding.categories.dto;

import com.example.preonboarding.categories.domain.Categories;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ProductCategoriesDTO {
    private Long id;
    private String slug;
    private String description;
    private String imageUrl;
    private boolean isPrimary;
    private CategoriesDTO parent;

    public ProductCategoriesDTO(Categories categories, boolean primary, Categories parent) {
        this.id = categories.getId();
        this.slug = categories.getSlug();
        this.description = categories.getDescription();
        this.imageUrl = categories.getImageUrl();
        this.isPrimary = primary;
        if (parent != null) {
            this.parent = CategoriesDTO.builder()
                    .id(parent.getId())
                    .name(parent.getName())
                    .slug(parent.getSlug())
                    .build();
        }else{
            this.parent = null;
        }

    }
}
