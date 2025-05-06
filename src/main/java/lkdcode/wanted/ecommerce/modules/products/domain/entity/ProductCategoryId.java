package lkdcode.wanted.ecommerce.modules.products.domain.entity;

import java.util.Objects;

public record ProductCategoryId(
    Long value
) {

    public ProductCategoryId(Long value) {
        this.value = Objects.requireNonNull(value);
    }
}