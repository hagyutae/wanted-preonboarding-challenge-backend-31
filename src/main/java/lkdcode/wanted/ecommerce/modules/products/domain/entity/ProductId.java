package lkdcode.wanted.ecommerce.modules.products.domain.entity;

import java.util.Objects;

public record ProductId(
    Long value
) {
    public ProductId(Long value) {
        this.value = Objects.requireNonNull(value);
    }
}