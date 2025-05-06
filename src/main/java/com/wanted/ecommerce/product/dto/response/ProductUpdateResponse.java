package com.wanted.ecommerce.product.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wanted.ecommerce.product.domain.Product;
import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record ProductUpdateResponse(
    Long id,
    String name,
    String slug,
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    LocalDateTime updatedAt
) {

    public static ProductUpdateResponse of(Product product) {
        return ProductUpdateResponse.builder()
            .id(product.getId())
            .name(product.getName())
            .slug(product.getSlug())
            .updatedAt(product.getUpdatedAt())
            .build();
    }
}
