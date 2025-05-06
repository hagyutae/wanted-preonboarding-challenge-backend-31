package lkdcode.wanted.ecommerce.modules.products.domain.entity;

import java.util.Objects;

public record ProductSellerId(
    Long value
) {
    public ProductSellerId(Long value) {
        this.value = Objects.requireNonNull(value);
    }
}
