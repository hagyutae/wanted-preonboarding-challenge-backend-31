package com.example.wanted_preonboarding_challenge_backend_31.shared.dto.product;

import java.util.List;

public record ProductOptionGroupDetailDto(
        Long id,
        String name,
        int displayOrder,
        List<ProductOptionDetailDto> options
) {
}
