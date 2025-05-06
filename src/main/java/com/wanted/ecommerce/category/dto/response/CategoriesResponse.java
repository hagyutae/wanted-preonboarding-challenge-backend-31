package com.wanted.ecommerce.category.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;
import lombok.Builder;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record CategoriesResponse(
    Long id,
    String name,
    String slug,
    String description,
    Integer level,
    String imageUrl,
    List<CategoriesResponse> children
) {

    public static CategoriesResponse of(Long id, String name, String slug, String description,
        Integer level, String imageUrl, List<CategoriesResponse> children) {
        return CategoriesResponse.builder()
            .id(id)
            .name(name)
            .slug(slug)
            .description(description)
            .level(level)
            .imageUrl(imageUrl)
            .children(children)
            .build();
    }
}
