package com.example.cqrsapp.product.dto.response;

import com.example.cqrsapp.product.domain.Category;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CategoryResponse {

    private long id;
    @JsonIgnoreProperties
    private Long parentId;
    private String name;
    private String slug;
    private String description;
    private String imageUrl;
    private List<CategoryResponse> childCategories = new ArrayList<>();;

   @Builder
    public CategoryResponse(long id, Long parentId, String name, String slug, String description, String imageUrl) {
        this.id = id;
        this.parentId = parentId;
        this.name = name;
        this.slug = slug;
        this.description = description;
        this.imageUrl = imageUrl;
    }

    public static List<CategoryResponse> fromEntity(List<Category> categories) {
        Map<Long, CategoryResponse> map = categories.stream().map(CategoryResponse::fromEntity).collect(Collectors.toMap(CategoryResponse::getId, Function.identity()));
        map.values().stream().filter(CategoryResponse::hasParentId).forEach(category -> {
            CategoryResponse parentCategory = map.getOrDefault(category.getParentId(), null);
            if (parentCategory != null) {
                parentCategory.getChildCategories().add(category);
            }
        });

        return map.values().stream().filter(category -> !category.hasParentId()).sorted(Comparator.comparing(CategoryResponse::getId)).toList();
    }

    public static CategoryResponse fromEntity(Category category) {
        return CategoryResponse.builder()
                .id(category.getId())
                .parentId(category.getParent() != null? category.getParent().getId() : null)
                .name(category.getName())
                .slug(category.getSlug())
                .description(category.getDescription())
                .imageUrl(category.getImageUrl())
                .build();
    }

    private  boolean hasParentId() {
        return this.parentId != null;
    }
}
