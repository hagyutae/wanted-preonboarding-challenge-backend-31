package com.example.wanted_preonboarding_challenge_backend_31.web.category.dto.response;

import com.example.wanted_preonboarding_challenge_backend_31.shared.dto.category.CategoryDetailDto;
import com.example.wanted_preonboarding_challenge_backend_31.shared.dto.pagination.PaginationRes;
import com.example.wanted_preonboarding_challenge_backend_31.shared.dto.product.ProductSearchDataDto;
import java.util.List;

public record CategoryProductRes(
        CategoryDetailDto category,
        List<ProductSearchDataDto> items,
        PaginationRes pagination
) {
}
