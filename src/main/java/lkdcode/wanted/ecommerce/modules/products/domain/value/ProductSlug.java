package lkdcode.wanted.ecommerce.modules.products.domain.value;

import java.util.Objects;

public record ProductSlug(
    String value
) {
    public static final int LENGTH = 255;
    public static final String LENGTH_ERROR_MESSAGE = "상품의 슬러그는 최대 255자까지 가능합니다.";

    public ProductSlug(String value) {
        this.value = Objects.requireNonNull(value);
        if (this.value.length() > LENGTH) throw new IllegalArgumentException(LENGTH_ERROR_MESSAGE);
    }
}