package com.example.wanted_preonboarding_challenge_backend_31.web.category.dto.response;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
public class CategorySearchRes {
    private final Long id;
    private final String name;
    private final String slug;
    private final String description;
    private final int level;
    private final String imageUrl;
    @Setter
    private List<CategorySearchRes> children = new ArrayList<>();

    public CategorySearchRes(Long id, String name, String slug, String description, int level, String imageUrl) {
        this.id = id;
        this.name = name;
        this.slug = slug;
        this.description = description;
        this.level = level;
        this.imageUrl = imageUrl;
    }
}
