package lkdcode.wanted.ecommerce.modules.products.domain.value.detail;

import java.util.Map;
import java.util.Objects;

public record ProductDimension(
    Map<String, Object> value
) {
    public ProductDimension(Map<String, Object> value) {
        this.value = Objects.requireNonNull(value);
    }
}
