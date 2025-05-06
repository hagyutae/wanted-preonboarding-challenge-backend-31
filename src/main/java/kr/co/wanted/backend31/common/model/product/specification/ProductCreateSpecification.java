package kr.co.wanted.backend31.common.model.product.specification;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import kr.co.wanted.backend31.common.model.product.Product;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Builder
public record ProductCreateSpecification(String name, String slug, String shortDescription, String fullDescription,
        Long sellerId, Long brandId, String status, ProductDetailCreateSpecification detailSpec,
        ProductPriceCreateSpecification priceSpec, List<ProductCategoryCreateSpecification> categorySpecs,
        List<ProductOptionGroupCreateSpecification> optionGroupSpecs, List<ProductImageCreateSpecification> imageSpecs,
        List<ProductTagCreateSpecification> tagSpecs) implements Predicate<Product> {

    public ProductCreateSpecification {
        Objects.requireNonNull(name);
        Objects.requireNonNull(slug);
        Objects.requireNonNull(shortDescription);
        Objects.requireNonNull(fullDescription);
        Objects.requireNonNull(status);
        Objects.requireNonNull(detailSpec);
        Objects.requireNonNull(priceSpec);
        Objects.requireNonNull(sellerId);
        Objects.requireNonNull(brandId);
        categorySpecs = Collections.unmodifiableList(categorySpecs);
        categorySpecs.forEach(Objects::requireNonNull);
        optionGroupSpecs = Collections.unmodifiableList(optionGroupSpecs);
        optionGroupSpecs.forEach(Objects::requireNonNull);
        imageSpecs = Collections.unmodifiableList(imageSpecs);
        imageSpecs.forEach(Objects::requireNonNull);
        tagSpecs = Collections.unmodifiableList(tagSpecs);
        tagSpecs.forEach(Objects::requireNonNull);
    }

    @Override
    public boolean test(Product t) {
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
                && slug.equals(t.getSlug())
                && shortDescription.equals(t.getShortDescription())
                && fullDescription.equals(t.getFullDescription())
                && sellerId.equals(t.getSeller().getId())
                && brandId.equals(t.getBrand().getId())
                && status.equals(t.getStatus())
                && detailSpec.test(t.getDetail())
                && priceSpec.test(t.getPrice())
                && Tuple.zip(categorySpecs, t.getCategories()).stream().allMatch(Tuple::test)
                && Tuple.zip(optionGroupSpecs, t.getOptionGroups()).stream().allMatch(Tuple::test)
                && Tuple.zip(imageSpecs, t.getImages()).stream().allMatch(Tuple::test)
                && Tuple.zip(tagSpecs, t.getTags()).stream().allMatch(Tuple::test);
        if (!result) {
            log.warn("validation fails");
        }
        return result;
    }

}
