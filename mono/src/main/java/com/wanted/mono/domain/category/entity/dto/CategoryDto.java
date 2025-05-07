package com.wanted.mono.domain.category.entity.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.wanted.mono.domain.category.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CategoryDto {
    private Long id;
    private String name;
    private String slug;
    private String description;
    private Integer level;
    @JsonProperty("image_url")
    private String imageUrl;

    private List<CategoryDto> children;

    // -------------------

    public static CategoryDto of(Category category) {
        CategoryDto dto = new CategoryDto();
        dto.setId(category.getId());
        dto.setName(category.getName());
        dto.setSlug(category.getSlug());
        dto.setDescription(category.getDescription());
        dto.setLevel(category.getLevel());
        dto.setImageUrl(category.getImageUrl());
        return dto;
    }
}
