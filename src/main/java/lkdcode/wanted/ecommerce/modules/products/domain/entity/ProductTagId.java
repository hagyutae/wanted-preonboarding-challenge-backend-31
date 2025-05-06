package lkdcode.wanted.ecommerce.modules.products.domain.entity;

import java.util.Objects;

public record ProductTagId(
    Long value
) {

    public ProductTagId(Long value) {
        this.value = Objects.requireNonNull(value);
    }
}