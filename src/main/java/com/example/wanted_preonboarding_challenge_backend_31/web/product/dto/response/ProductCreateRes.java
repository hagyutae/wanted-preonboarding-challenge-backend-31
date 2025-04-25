package com.example.wanted_preonboarding_challenge_backend_31.web.product.dto.response;

import com.example.wanted_preonboarding_challenge_backend_31.domain.model.product.Product;
import java.time.LocalDateTime;

public record ProductCreateRes(
        Long id,
        String name,
        String slug,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {

    public static ProductCreateRes from(Product product) {
        return new ProductCreateRes(
                product.getId(),
                product.getName(),
                product.getSlug(),
                product.getCreatedAt(),
                product.getUpdatedAt()
        );
    }
}
