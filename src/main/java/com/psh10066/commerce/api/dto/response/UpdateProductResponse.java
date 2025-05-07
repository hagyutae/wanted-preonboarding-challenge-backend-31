package com.psh10066.commerce.api.dto.response;

import java.time.LocalDateTime;

public record UpdateProductResponse(
    Long id,
    String name,
    String slug,
    LocalDateTime updatedAt
) {
}
