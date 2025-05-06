package lkdcode.wanted.ecommerce.modules.products.domain.value.detail;

import java.util.Objects;

public record ProductWarrantyInfo(
    String value
) {
    public ProductWarrantyInfo(String value) {
        this.value = Objects.requireNonNull(value);
    }
}
