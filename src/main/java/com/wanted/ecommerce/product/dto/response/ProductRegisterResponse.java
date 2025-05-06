package com.wanted.ecommerce.product.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wanted.ecommerce.product.domain.Product;
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

    public static ProductRegisterResponse of(Product product) {
        return ProductRegisterResponse.builder()
            .id(product.getId())
            .name(product.getName())
            .slug(product.getSlug())
            .createdAt(product.getCreatedAt())
            .updatedAt(product.getUpdatedAt())
            .build();
    }
}
