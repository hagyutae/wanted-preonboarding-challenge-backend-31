package com.example.preonboarding.categories.response;

import com.example.preonboarding.categories.domain.Categories;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CategoriesResponse {
    private Long id;
    private String name;
    private String slug;
    private String description;
    private Integer level;
    private String imageUrl;
    private List<CategoriesResponse> children = new ArrayList<>();

    public CategoriesResponse(Long id, String name, String slug, String description, Integer level, String imageUrl) {
        this.id = id;
        this.name = name;
        this.slug = slug;
        this.description = description;
        this.level = level;
        this.imageUrl = imageUrl;
    }
}
