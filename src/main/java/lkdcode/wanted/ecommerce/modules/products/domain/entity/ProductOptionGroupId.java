package lkdcode.wanted.ecommerce.modules.products.domain.entity;

import java.util.Objects;

public record ProductOptionGroupId(
    Long value
) {

    public ProductOptionGroupId(Long value) {
        this.value = Objects.requireNonNull(value);
    }
}