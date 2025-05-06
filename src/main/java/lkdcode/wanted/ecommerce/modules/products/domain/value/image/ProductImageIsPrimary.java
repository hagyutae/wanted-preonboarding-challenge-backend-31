package lkdcode.wanted.ecommerce.modules.products.domain.value.image;

import java.util.Objects;

public record ProductImageIsPrimary(
    Boolean value
) {
    public static final boolean DEFAULT = false;

    public ProductImageIsPrimary(Boolean value) {
        this.value = Objects.requireNonNull(value);
    }

    public static ProductImageIsPrimary init() {
        return new ProductImageIsPrimary(DEFAULT);
    }
}