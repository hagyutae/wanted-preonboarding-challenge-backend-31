package lkdcode.wanted.ecommerce.modules.products.domain.value.tag;

import java.util.Objects;

public record ProductTag(
    Long value
) {

    public ProductTag(Long value) {
        this.value = Objects.requireNonNull(value);
    }
}
