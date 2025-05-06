package lkdcode.wanted.ecommerce.modules.products.domain.value.option;

import java.util.Objects;

public record ProductOptionSku(
    String value
) {
    public static final int LENGTH = 100;
    public static final String LENGTH_ERROR_MESSAGE = "재고 관리 코드명은 최대 100 가능합니다.";

    public ProductOptionSku(String value) {
        this.value = Objects.requireNonNull(value);
        if (value.length() > LENGTH) throw new IllegalArgumentException(LENGTH_ERROR_MESSAGE);
    }
}
