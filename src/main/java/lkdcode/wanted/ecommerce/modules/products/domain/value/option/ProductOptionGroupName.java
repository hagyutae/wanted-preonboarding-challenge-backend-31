package lkdcode.wanted.ecommerce.modules.products.domain.value.option;

import java.util.Objects;

public record ProductOptionGroupName(
    String value
) {
    public static final int LENGTH = 100;

    public ProductOptionGroupName(String value) {
        this.value = Objects.requireNonNull(value);
        if (value.length() > LENGTH) throw new IllegalArgumentException();
    }
}