package kr.co.wanted.backend31.service;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.wanted.backend31.common.error.InvalidInputException;
import kr.co.wanted.backend31.common.error.ResourceNotFoundException;
import kr.co.wanted.backend31.common.model.category.Category;
import kr.co.wanted.backend31.common.model.product.Product;
import kr.co.wanted.backend31.common.model.product.ProductCategory;
import kr.co.wanted.backend31.common.model.product.ProductDetail;
import kr.co.wanted.backend31.common.model.product.ProductImage;
import kr.co.wanted.backend31.common.model.product.ProductOption;
import kr.co.wanted.backend31.common.model.product.ProductOptionGroup;
import kr.co.wanted.backend31.common.model.product.ProductPrice;
import kr.co.wanted.backend31.common.model.product.ProductTag;
import kr.co.wanted.backend31.common.model.product.specification.ProductCategoryCreateSpecification;
import kr.co.wanted.backend31.common.model.product.specification.ProductCreateSpecification;
import kr.co.wanted.backend31.common.model.product.specification.ProductTagCreateSpecification;
import kr.co.wanted.backend31.common.model.tag.Tag;
import kr.co.wanted.backend31.repository.BrandRepository;
import kr.co.wanted.backend31.repository.SellerRepository;
import kr.co.wanted.backend31.repository.category.CategoryRepository;
import kr.co.wanted.backend31.repository.product.ProductRepository;
import kr.co.wanted.backend31.repository.tag.TagRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductManagementService {
        private final TagRepository tagRepository;
        private final BrandRepository BrandRepository;
        private final SellerRepository sellerRepository;
        private final ProductRepository productRepository;
        private final CategoryRepository categoryRepository;

        @Transactional
        public Product create(ProductCreateSpecification spec) {
                final var seller = sellerRepository.findById(spec.sellerId())
                                .orElseThrow(() -> new ResourceNotFoundException(Map.of("sellerId", spec.sellerId())));
                final var brand = BrandRepository.findById(spec.brandId())
                                .orElseThrow(() -> new ResourceNotFoundException(Map.of("brandId", spec.brandId())));

                final var detail = ProductDetail.builder()
                                .weight(spec.detailSpec().weight())
                                .dimensions(spec.detailSpec().dimensions())
                                .materials(spec.detailSpec().materials())
                                .countryOfOrigin(spec.detailSpec().countryOfOrigin())
                                .warrantyInfo(spec.detailSpec().warrantyInfo())
                                .careInstructions(spec.detailSpec().careInstructions())
                                .additionalInfo(spec.detailSpec().additionalInfo())
                                .build();

                final var price = ProductPrice.builder()
                                .basePrice(spec.priceSpec().basePrice())
                                .salePrice(spec.priceSpec().salePrice())
                                .costPrice(spec.priceSpec().costPrice())
                                .currency(spec.priceSpec().currency())
                                .taxRate(spec.priceSpec().taxRate())
                                .build();

                final var categories = spec.categorySpecs().stream()
                                .map(ProductCategoryCreateSpecification::categoryId)
                                .map(categoryRepository::findById)
                                .filter(Optional::isPresent)
                                .map(Optional::get)
                                .toList();

                if (spec.categorySpecs().size() != categories.size()) {
                        final Set<Long> desiredCategoryIds = spec.categorySpecs().stream()
                                        .map(ProductCategoryCreateSpecification::categoryId)
                                        .collect(Collectors.toSet());

                        final Set<Long> actualCategoryIds = categories.stream()
                                        .map(Category::getId)
                                        .collect(Collectors.toSet());

                        final var missCategoryIds = desiredCategoryIds.stream()
                                        .filter(e -> !actualCategoryIds.contains(e))
                                        .toList();

                        throw new ResourceNotFoundException(Map.of("categoryIds", missCategoryIds));
                }

                final var productCategories = IntStream.range(0, spec.categorySpecs().size())
                                .boxed()
                                .collect(Collectors.toMap(spec.categorySpecs()::get, categories::get, (x, y) -> y,
                                                LinkedHashMap::new))
                                .entrySet().stream()
                                .map(e -> ProductCategory.builder().category(e.getValue())
                                                .isPrimary(e.getKey().isPrimary()).build())
                                .toList();

                final var optionGroups = spec.optionGroupSpecs().stream()
                                .map(optionGroupSpec -> ProductOptionGroup.builder()
                                                .name(optionGroupSpec.name())
                                                .displayOrder(optionGroupSpec.displayOrder())
                                                .options(optionGroupSpec.optionSpecs().stream()
                                                                .map(optionSpec -> ProductOption.builder()
                                                                                .name(optionSpec.name())
                                                                                .additionalPrice(optionSpec
                                                                                                .additionalPrice())
                                                                                .sku(optionSpec.sku())
                                                                                .stock(optionSpec.stock())
                                                                                .displayOrder(optionSpec.displayOrder())
                                                                                .build())
                                                                .toList())
                                                .build())
                                .toList();

                final var images = spec.imageSpecs().stream()
                                .map(imageSpec -> ProductImage.builder()
                                                .url(imageSpec.url())
                                                .altText(imageSpec.altText())
                                                .isPrimary(imageSpec.isPrimary())
                                                .displayOrder(imageSpec.displayOrder())
                                                .build())
                                .toList();

                final var tags = spec.tagSpecs().stream()
                                .map(ProductTagCreateSpecification::tagId)
                                .map(tagRepository::findById)
                                .filter(Optional::isPresent)
                                .map(Optional::get)
                                .toList();

                if (spec.tagSpecs().size() != tags.size()) {
                        final Set<Long> desiredTagIds = spec.tagSpecs().stream()
                                        .map(ProductTagCreateSpecification::tagId)
                                        .collect(Collectors.toSet());

                        final Set<Long> actualTagIds = tags.stream()
                                        .map(Tag::getId)
                                        .collect(Collectors.toSet());

                        final var missTagIds = desiredTagIds.stream()
                                        .filter(e -> !actualTagIds.contains(e))
                                        .toList();

                        throw new ResourceNotFoundException(Map.of("tagIds", missTagIds));
                }
                final var productTags = tags.stream()
                                .map(tag -> ProductTag.builder().tag(tag).build())
                                .toList();

                final var product = Product.builder()
                                .name(spec.name())
                                .slug(spec.slug())
                                .shortDescription(spec.shortDescription())
                                .fullDescription(spec.fullDescription())
                                .status(spec.status())
                                .seller(seller)
                                .brand(brand)
                                .detail(detail)
                                .price(price)
                                .categories(productCategories)
                                .optionGroups(optionGroups)
                                .images(images)
                                .tags(productTags)
                                .build();

                if (!spec.test(product)) {
                        throw new InvalidInputException(Map.of("reason", "spec validation fails"));
                }

                product.syncAggregation();
                productRepository.save(product);
                return product;
        }

        @Transactional
        public void delete(Long productId) {
                final var product = productRepository.findById(productId)
                                .orElseThrow(() -> new ResourceNotFoundException(Map.of("productId", productId)));
                productRepository.delete(product);
        }
}
