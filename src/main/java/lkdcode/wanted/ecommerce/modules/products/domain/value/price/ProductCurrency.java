package lkdcode.wanted.ecommerce.modules.products.domain.value.price;

import java.util.Objects;

public record ProductCurrency(
    String value
) {

    public static final int LENGTH = 3;
    public static final String DEFAULT = "KRW";

    public ProductCurrency(String value) {
        this.value = Objects.requireNonNull(value);
        if (value.length() > LENGTH) throw new IllegalArgumentException();
    }

    public static ProductCurrency init() {
        return new ProductCurrency(DEFAULT);
    }
}