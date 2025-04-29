package com.example.wanted_preonboarding_challenge_backend_31.web.category.dto.response;

import java.util.List;

public record CategorySearchRes(
        Long id,
        String name,
        String slug,
        String description,
        int level,
        String imageUrl,
        List<CategorySearchRes> children
) {
}
