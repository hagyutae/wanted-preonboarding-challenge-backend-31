package lkdcode.wanted.ecommerce.modules.products.domain.value.image;

import java.util.Objects;

public record ProductImageUrl(
    String value
) {
    public static final int LENGTH = 255;
    public static final String LENGTH_ERROR_MESSAGE = "이미지 URL은 최대 255자까지 허용됩니다.";

    public ProductImageUrl(String value) {
        this.value = Objects.requireNonNull(value);
        if (value.length() > LENGTH) throw new IllegalArgumentException(LENGTH_ERROR_MESSAGE);
    }
}
