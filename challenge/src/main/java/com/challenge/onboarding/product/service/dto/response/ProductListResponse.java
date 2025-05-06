package com.challenge.onboarding.product.service.dto.response;

import com.challenge.onboarding.common.global.Pagination;

import java.util.List;

public record ProductListResponse(
        List<ProductResponse> items,
        Pagination pagination
) {
}
