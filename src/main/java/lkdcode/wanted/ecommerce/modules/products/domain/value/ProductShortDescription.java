package lkdcode.wanted.ecommerce.modules.products.domain.value;

import java.util.Objects;

public record ProductShortDescription(
    String value
) {
    public static final int LENGTH = 500;
    public static final String LENGTH_ERROR_MESSAGE = "상품의 요약 설명은 최대 500자까지 가능합니다.";

    public ProductShortDescription(String value) {
        this.value = Objects.requireNonNull(value);
        if (this.value.length() > LENGTH) throw new IllegalArgumentException(LENGTH_ERROR_MESSAGE);
    }
}