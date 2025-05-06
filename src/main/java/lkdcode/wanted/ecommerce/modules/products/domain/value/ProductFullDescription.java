package lkdcode.wanted.ecommerce.modules.products.domain.value;

import java.util.Objects;

public record ProductFullDescription(
    String value
) {

    public ProductFullDescription(String value) {
        this.value = Objects.requireNonNull(value);
    }
}