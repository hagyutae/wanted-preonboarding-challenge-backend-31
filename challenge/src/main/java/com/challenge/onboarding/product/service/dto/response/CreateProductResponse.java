package com.challenge.onboarding.product.service.dto.response;

import java.time.LocalDateTime;

public record CreateProductResponse(
    Long id,
    String name,
    String slug,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {}
