package com.psh10066.commerce.api.dto.response;

import java.util.List;

public record GetMainResponse(
    List<GetAllProductsResponse> newProducts,
    List<GetAllProductsResponse> popularProducts,
    List<CategoryDto> featuredCategories
) {
    public record CategoryDto(
        Long id,
        String name,
        String slug,
        String imageUrl,
        Integer productCount
    ) {
    }
}
