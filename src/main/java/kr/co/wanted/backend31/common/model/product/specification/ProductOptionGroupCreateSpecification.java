package kr.co.wanted.backend31.common.model.product.specification;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import kr.co.wanted.backend31.common.model.product.ProductOptionGroup;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

/**
 * ProductOptionGroupCreateSpecification
 */
@Slf4j
@Builder
public record ProductOptionGroupCreateSpecification(String name, Integer displayOrder,
        List<ProductOptionCreateSpecification> optionSpecs) implements Predicate<ProductOptionGroup> {

    public ProductOptionGroupCreateSpecification {
        Objects.requireNonNull(name);
        Objects.requireNonNull(displayOrder);
        optionSpecs = Collections.unmodifiableList(optionSpecs);
        optionSpecs.forEach(Objects::requireNonNull);
    }

    @Override
    public boolean test(ProductOptionGroup t) {
        record Tuple<T, P extends Predicate<T>>(T target, P spec) {
            public Tuple {
                Objects.requireNonNull(target);
                Objects.requireNonNull(spec);
            }

            public Tuple(Map.Entry<T, P> entry) {
                this(entry.getKey(), entry.getValue());
            }

            public static <T, P extends Predicate<T>> List<Tuple<T, P>> zip(List<P> specs, List<T> targets) {
                return IntStream.range(0, specs.size())
                        .boxed()
                        .collect(Collectors.toMap(targets::get, specs::get))
                        .entrySet().stream()
                        .map(Tuple::new)
                        .toList();
            }

            public boolean test() {
                return spec.test(target);
            }
        }
        final var result = name.equals(t.getName())
                && displayOrder.equals(t.getDisplayOrder())
                && Tuple.zip(optionSpecs, t.getOptions()).stream().allMatch(Tuple::test);
        if (!result) {
            log.warn("validation fails");
        }
        return result;
    }

}
