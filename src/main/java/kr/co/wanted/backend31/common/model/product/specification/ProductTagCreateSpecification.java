package kr.co.wanted.backend31.common.model.product.specification;

import java.util.Objects;
import java.util.function.Predicate;

import kr.co.wanted.backend31.common.model.product.ProductTag;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

/**
 * ProductTagCreateSpecification
 */
@Slf4j
@Builder
public record ProductTagCreateSpecification(Long tagId) implements Predicate<ProductTag> {

    public ProductTagCreateSpecification {
        Objects.requireNonNull(tagId);
    }

    @Override
    public boolean test(ProductTag t) {
        final var result = tagId.equals(t.getTag().getId());
        if (!result) {
            log.warn("validation fails");
        }
        return result;
    }

}
