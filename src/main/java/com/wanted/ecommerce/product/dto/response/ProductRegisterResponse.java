package com.wanted.ecommerce.product.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record ProductRegisterResponse(
    Long id,
    String name,
    String slug,
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    LocalDateTime createdAt,
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    LocalDateTime updatedAt
) {

    public static ProductRegisterResponse of(long id, String name, String slug, LocalDateTime createdAt,
        LocalDateTime updatedAt) {
        return ProductRegisterResponse.builder()
            .id(id)
            .name(name)
            .slug(slug)
            .createdAt(createdAt)
            .updatedAt(updatedAt)
            .build();
    }
}
