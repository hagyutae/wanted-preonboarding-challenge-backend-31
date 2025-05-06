package lkdcode.wanted.ecommerce.modules.products.domain.value.category;

import java.util.Objects;

public record ProductCategoryIsPrimary(
    Boolean value
) {
    public static final boolean DEFAULT = false;

    public ProductCategoryIsPrimary(Boolean value) {
        this.value = Objects.requireNonNull(value);
    }

    public static ProductCategoryIsPrimary init() {
        return new ProductCategoryIsPrimary(DEFAULT);
    }
}