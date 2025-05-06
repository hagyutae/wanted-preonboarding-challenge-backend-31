package lkdcode.wanted.ecommerce.modules.products.domain.value.option;

import java.util.Objects;

public record ProductOptionStock(
    Integer value
) {
    public static final int DEFAULT = 0;

    public ProductOptionStock(Integer value) {
        this.value = Objects.requireNonNull(value);
    }

    public static ProductOptionStock init() {
        return new ProductOptionStock(DEFAULT);
    }
}