package lkdcode.wanted.ecommerce.modules.products.domain.entity;

import java.util.Objects;

public record ProductBrandId(
    Long value
) {
    public ProductBrandId(Long value) {
        this.value = Objects.requireNonNull(value);
    }
}
