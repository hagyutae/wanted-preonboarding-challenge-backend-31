package lkdcode.wanted.ecommerce.modules.products.application.usecase.command;

import lkdcode.wanted.ecommerce.modules.products.domain.entity.ProductId;
import lkdcode.wanted.ecommerce.modules.products.domain.value.ProductName;
import lkdcode.wanted.ecommerce.modules.products.domain.value.ProductSlug;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.Objects;

public record UpsertResult(
    ProductId id,
    ProductName name,
    ProductSlug slug,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {

    @Builder
    public UpsertResult(ProductId id, ProductName name, ProductSlug slug, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = Objects.requireNonNull(id);
        this.name = Objects.requireNonNull(name);
        this.slug = Objects.requireNonNull(slug);
        this.createdAt = Objects.requireNonNull(createdAt);
        this.updatedAt = Objects.requireNonNull(updatedAt);
    }
}