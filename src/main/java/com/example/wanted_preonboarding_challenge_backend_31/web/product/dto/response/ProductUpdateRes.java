package com.example.wanted_preonboarding_challenge_backend_31.web.product.dto.response;

import com.example.wanted_preonboarding_challenge_backend_31.domain.model.product.Product;
import java.time.LocalDateTime;

public record ProductUpdateRes(
        Long id,
        String name,
        String slug,
        LocalDateTime updatedAt
) {

    public static ProductUpdateRes from(Product product) {
        return new ProductUpdateRes(
                product.getId(),
                product.getName(),
                product.getSlug(),
                product.getUpdatedAt()
        );
    }
}
