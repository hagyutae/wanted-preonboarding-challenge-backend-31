package com.preonboarding.dto.response.category;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.preonboarding.domain.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryResponse {
    private Long id;
    private String name;
    private String slug;
    private String description;
    private Integer level;
    private String imageUrl;
    private ParentResponse parent;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<CategoryResponse> children = new ArrayList<>();

    public static CategoryResponse of(Category category) {
        ParentResponse parentResponse = null;
        if (category.getParent() != null) {
            parentResponse = ParentResponse.of(category.getParent());
        }

        return CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .slug(category.getSlug())
                .description(category.getDescription())
                .level(category.getLevel())
                .imageUrl(category.getImageUrl())
                .parent(parentResponse)
                .build();
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    private static class ParentResponse {
        private Long id;
        private String name;
        private String slug;

        public static ParentResponse of(Category parentCategory) {
            return ParentResponse.builder()
                    .id(parentCategory.getId())
                    .name(parentCategory.getName())
                    .slug(parentCategory.getSlug())
                    .build();
        }
    }
}
