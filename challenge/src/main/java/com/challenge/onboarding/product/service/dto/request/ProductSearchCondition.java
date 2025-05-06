package com.challenge.onboarding.product.service.dto.request;

import java.util.List;

public record ProductSearchCondition(
        int page,
        int perPage,
        String sort,
        String status,
        Integer minPrice,
        Integer maxPrice,
        List<Integer> category,
        Integer seller,
        Integer brand,
        Boolean inStock,
        String search
) {
}
