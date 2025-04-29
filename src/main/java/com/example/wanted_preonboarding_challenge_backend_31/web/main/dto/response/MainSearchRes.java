package com.example.wanted_preonboarding_challenge_backend_31.web.main.dto.response;

import com.example.wanted_preonboarding_challenge_backend_31.shared.dto.category.CategoryFeaturedDto;
import com.example.wanted_preonboarding_challenge_backend_31.shared.dto.product.ProductSearchDataDto;
import java.util.List;

public record MainSearchRes(
        List<ProductSearchDataDto> newProducts,
        List<ProductSearchDataDto> popularProducts,
        List<CategoryFeaturedDto> featuredCategories
) {
}
