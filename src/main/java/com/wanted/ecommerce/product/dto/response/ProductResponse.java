package com.wanted.ecommerce.product.dto.response;

import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record ProductResponse(
    Long id,
    String name,
    String slug,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {

    public static ProductResponse of(long id, String name, String slug, LocalDateTime createdAt,
        LocalDateTime updatedAt) {
        return ProductResponse.builder()
            .id(id)
            .name(name)
            .slug(slug)
            .createdAt(createdAt)
            .updatedAt(updatedAt)
            .build();
    }
}
