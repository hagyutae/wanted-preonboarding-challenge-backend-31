package lkdcode.wanted.ecommerce.modules.products.domain.value.detail;

import java.util.Objects;

public record ProductCountryOfOrigin(
    String value
) {
    public static final int LENGTH = 100;

    public ProductCountryOfOrigin(String value) {
        this.value = Objects.requireNonNull(value);
        if (value.length() > LENGTH) throw new IllegalArgumentException();
    }
}
