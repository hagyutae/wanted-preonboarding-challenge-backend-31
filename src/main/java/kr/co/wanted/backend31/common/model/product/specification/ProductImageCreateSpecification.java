package kr.co.wanted.backend31.common.model.product.specification;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;

import kr.co.wanted.backend31.common.model.product.ProductImage;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

/**
 * ProductImageCreateSpecification
 */
@Slf4j
@Builder
public record ProductImageCreateSpecification(String url, String altText, boolean isPrimary, Integer displayOrder,
        Long optionId) implements Predicate<ProductImage> {

    public ProductImageCreateSpecification {
        Objects.requireNonNull(url);
        Objects.requireNonNull(altText);
        Objects.requireNonNull(displayOrder);
    }

    @Override
    public boolean test(ProductImage t) {
        final var result = url.equals(t.getUrl())
                && altText.equals(t.getAltText())
                && isPrimary == t.isPrimary()
                && displayOrder.equals(t.getDisplayOrder())
                && Optional.ofNullable(optionId).stream().allMatch(id -> id.equals(t.getOption().getId()));
        if (!result) {
            log.warn("validation fails");
        }
        return result;
    }
}
