package lkdcode.wanted.ecommerce.modules.products.domain.value.image;

import java.util.Objects;

public record ProductImageDisplayOrder(
    Integer value
) {
    public static final int DEFAULT = 0;

    public ProductImageDisplayOrder(Integer value) {
        this.value = Objects.requireNonNull(value);
    }

    public static ProductImageDisplayOrder init() {
        return new ProductImageDisplayOrder(DEFAULT);
    }
}