package kr.co.wanted.backend31.common.model.product.specification;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.function.Predicate;

import kr.co.wanted.backend31.common.model.product.ProductOption;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

/**
 * ProductOptionCreateSpecification
 */
@Slf4j
@Builder
public record ProductOptionCreateSpecification(String name, BigDecimal additionalPrice, String sku, Integer stock,
        Integer displayOrder) implements Predicate<ProductOption> {

    public ProductOptionCreateSpecification {
        Objects.requireNonNull(name);
        Objects.requireNonNull(additionalPrice);
        Objects.requireNonNull(sku);
        Objects.requireNonNull(stock);
        Objects.requireNonNull(displayOrder);
    }

    @Override
    public boolean test(ProductOption t) {
        final var result = name.equals(t.getName())
                && additionalPrice.equals(t.getAdditionalPrice())
                && sku.equals(t.getSku())
                && stock.equals(t.getStock())
                && displayOrder.equals(t.getDisplayOrder());
        if (!result) {
            log.warn("validation fails");
        }
        return result;
    }

}
