package lkdcode.wanted.ecommerce.modules.products.domain.value.detail;

import java.util.Objects;

public record ProductMaterial(
    String value
) {
    public ProductMaterial(String value) {
        this.value = Objects.requireNonNull(value);
    }
}