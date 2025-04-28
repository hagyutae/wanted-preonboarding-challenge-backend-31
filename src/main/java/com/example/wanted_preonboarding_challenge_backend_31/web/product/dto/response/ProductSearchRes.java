package com.example.wanted_preonboarding_challenge_backend_31.web.product.dto.response;

import com.example.wanted_preonboarding_challenge_backend_31.shared.dto.pagination.PaginationRes;
import java.util.List;

public record ProductSearchRes(
        List<ProductSearchDataRes> items,
        PaginationRes pagination
) {
}
