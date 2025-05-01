package com.wanted.mono.domain.product.dto.response;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ProductSaveResponse {
    private Long id;
    private String name;
    private String slug;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public ProductSaveResponse(Long id, String name, String slug, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.slug = slug;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
