package kr.co.wanted.backend31.common.model.product.specification;

import java.util.Objects;
import java.util.function.Predicate;

import kr.co.wanted.backend31.common.model.product.ProductCategory;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

/**
 * ProductCategoryCreateSpecification
 */
@Slf4j
@Builder
public record ProductCategoryCreateSpecification(boolean isPrimary, Long categoryId)
        implements Predicate<ProductCategory> {

    public ProductCategoryCreateSpecification {
        Objects.requireNonNull(categoryId);
    }

    @Override
    public boolean test(ProductCategory t) {
        final var result = isPrimary == t.isPrimary()
                && categoryId.equals(t.getCategory().getId());
        if (!result) {
            log.warn("validation fails");
        }
        return result;
    }
}
