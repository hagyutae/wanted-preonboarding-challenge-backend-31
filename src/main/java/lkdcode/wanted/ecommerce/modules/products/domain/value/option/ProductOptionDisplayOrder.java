package lkdcode.wanted.ecommerce.modules.products.domain.value.option;

import java.util.Objects;

public record ProductOptionDisplayOrder(
    Integer value
) {
    public static final int DEFAULT = 0;

    public ProductOptionDisplayOrder(Integer value) {
        this.value = Objects.requireNonNull(value);
    }

    public static ProductOptionDisplayOrder init() {
        return new ProductOptionDisplayOrder(DEFAULT);
    }
}