package lkdcode.wanted.ecommerce.modules.products.domain.entity;

import java.util.Objects;

public record ProductOptionId(
    Long value
) {
    public ProductOptionId(Long value) {
        this.value = Objects.requireNonNull(value);
    }
}