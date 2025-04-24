package com.ecommerce.product.application.dto.res;

import java.time.LocalDateTime;

public record ProductCreatedResponse(
        Long id,
        String name,
        String slug,
        LocalDateTime created_at,
        LocalDateTime updated_at
) {
}
