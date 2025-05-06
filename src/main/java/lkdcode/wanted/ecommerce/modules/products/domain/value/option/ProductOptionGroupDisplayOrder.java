package lkdcode.wanted.ecommerce.modules.products.domain.value.option;

import java.util.Objects;

public record ProductOptionGroupDisplayOrder(
    Integer value
) {
    public static final int DEFAULT = 0;

    public ProductOptionGroupDisplayOrder(Integer value) {
        this.value = Objects.requireNonNull(value);
    }

    public static ProductOptionGroupDisplayOrder init() {
        return new ProductOptionGroupDisplayOrder(DEFAULT);
    }
}