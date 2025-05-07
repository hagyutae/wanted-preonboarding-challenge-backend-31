package com.challenge.onboarding.product.service.dto.response;

import com.challenge.onboarding.product.domain.model.Product;

import java.time.LocalDateTime;

public record CreateProductResponse(
    Long id,
    String name,
    String slug,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {
    public CreateProductResponse(Product product){
        this.id = product.getId();
        this.name = product.getName();
        this.slug = product.getSlug();
        this.createdAt = product.getCreatedAt();
        this.updatedAt = product.getUpdatedAt();
    }
}
