package lkdcode.wanted.ecommerce.modules.products.domain.value.detail;

import java.util.Objects;

public record ProductCareInstructions(
    String value
) {

    public ProductCareInstructions(String value) {
        this.value = Objects.requireNonNull(value);
    }
}