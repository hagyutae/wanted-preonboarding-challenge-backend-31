package lkdcode.wanted.ecommerce.modules.products.domain.value.option;

import java.util.Objects;

public record ProductOptionName(
    String value
) {
    public static final int LENGTH = 100;
    public static final String LENGTH_ERROR_MESSAGE = "옵션의 이름은 최대 100 가능합니다.";

    public ProductOptionName(String value) {
        this.value = Objects.requireNonNull(value);
        if (value.length() > LENGTH) throw new IllegalArgumentException(LENGTH_ERROR_MESSAGE);
    }
}