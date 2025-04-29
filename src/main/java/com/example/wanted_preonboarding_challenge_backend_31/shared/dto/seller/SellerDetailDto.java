package com.example.wanted_preonboarding_challenge_backend_31.shared.dto.seller;

import java.math.BigDecimal;

public record SellerDetailDto(
        Long id,
        String name,
        String description,
        String logoUrl,
        BigDecimal rating,
        String contactEmail,
        String contactPhone
) {
}
