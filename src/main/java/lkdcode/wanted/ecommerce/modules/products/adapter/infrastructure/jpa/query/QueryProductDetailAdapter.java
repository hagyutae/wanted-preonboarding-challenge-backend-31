package lkdcode.wanted.ecommerce.modules.products.adapter.infrastructure.jpa.query;

import com.querydsl.core.annotations.QueryProjection;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lkdcode.wanted.ecommerce.framework.query.QueryBase;
import lkdcode.wanted.ecommerce.framework.query.QueryDslUtil;
import lkdcode.wanted.ecommerce.modules.brand.adapter.infrastructure.jpa.entity.QBrandJpaEntity;
import lkdcode.wanted.ecommerce.modules.category.adapter.infrastructure.jpa.entity.QCategoryJpaEntity;
import lkdcode.wanted.ecommerce.modules.products.adapter.infrastructure.jpa.entity.*;
import lkdcode.wanted.ecommerce.modules.products.application.ports.out.query.QueryProductDetailOutPort;
import lkdcode.wanted.ecommerce.modules.products.application.usecase.query.dto.QueryProductBrandDTO;
import lkdcode.wanted.ecommerce.modules.products.application.usecase.query.dto.QueryProductSellerDTO;
import lkdcode.wanted.ecommerce.modules.products.application.usecase.query.dto.detail.*;
import lkdcode.wanted.ecommerce.modules.products.application.usecase.query.value.ParamCondition;
import lkdcode.wanted.ecommerce.modules.products.domain.entity.ProductId;
import lkdcode.wanted.ecommerce.modules.products.domain.value.ProductStatus;
import lkdcode.wanted.ecommerce.modules.reviews.adapter.infrastructure.jpa.entity.QReviewJpaEntity;
import lkdcode.wanted.ecommerce.modules.reviews.adapter.infrastructure.jpa.entity.ReviewJpaEntity;
import lkdcode.wanted.ecommerce.modules.sellers.adapter.infrastructure.jpa.entity.QSellerJpaEntity;
import lkdcode.wanted.ecommerce.modules.tag.adapter.infrastructure.jpa.entity.QTagJpaEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class QueryProductDetailAdapter extends QueryBase<ProductJpaEntity, QProductJpaEntity> implements QueryProductDetailOutPort {
    private static final QProductJpaEntity PRODUCT = QProductJpaEntity.productJpaEntity;
    private static final QProductDetailJpaEntity DETAIL = QProductDetailJpaEntity.productDetailJpaEntity;
    private static final QProductPriceJpaEntity PRICE = QProductPriceJpaEntity.productPriceJpaEntity;
    private static final QBrandJpaEntity BRAND = QBrandJpaEntity.brandJpaEntity;
    private static final QSellerJpaEntity SELLER = QSellerJpaEntity.sellerJpaEntity;

    private static final QProductCategoryJpaEntity PRODUCT_CATEGORY = QProductCategoryJpaEntity.productCategoryJpaEntity;
    private static final QCategoryJpaEntity CATEGORY = QCategoryJpaEntity.categoryJpaEntity;
    private static final QCategoryJpaEntity PARENT_CATEGORY = new QCategoryJpaEntity("PARENT_CATEGORY");

    private static final QProductOptionGroupJpaEntity PRODUCT_OPTION_GROUP = QProductOptionGroupJpaEntity.productOptionGroupJpaEntity;
    private static final QProductOptionJpaEntity PRODUCT_OPTION = QProductOptionJpaEntity.productOptionJpaEntity;
    private static final QProductOptionJpaEntity OPTION = QProductOptionJpaEntity.productOptionJpaEntity;

    private static final QProductImageJpaEntity IMAGE = QProductImageJpaEntity.productImageJpaEntity;

    private static final QProductTagJpaEntity PRODUCT_TAG = QProductTagJpaEntity.productTagJpaEntity;
    private static final QTagJpaEntity TAG = QTagJpaEntity.tagJpaEntity;

    private static final QReviewJpaEntity REVIEW = QReviewJpaEntity.reviewJpaEntity;

    public QueryProductDetailAdapter(JPAQueryFactory factory, QueryDslUtil queryDslUtil) {
        super(factory, queryDslUtil);
    }

    @Override
    public QueryProductDetailResult load(ProductId id) {
        final var result = loadProductDetail(id);
        final var categoryDTO = loadProductCategory(id);
        final var optionDTO = loadProductOption(id);
        final var imageDTO = loadProductImage(id);
        final var tagDTO = loadProductTag(id);
        final var reviewDTO = loadProductReview(id);
        final var relatedDTO = loadRelatedDTO(id, categoryDTO);

        return QueryProductDetailResult.builder()
            .id(result.id)
            .name(result.name)
            .slug(result.slug)
            .short_description(result.shortDescription)
            .full_description(result.fullDescription)
            .seller(convertSellerDTO(result))
            .brand(convertBrandDTO(result))
            .status(result.status)
            .created_at(result.createdAt)
            .updated_at(result.updatedAt)
            .detail(convertDetailDTO(result))
            .price(convertPriceDTO(result))
            .categories(categoryDTO)
            .option_groups(optionDTO)
            .images(imageDTO)
            .tags(tagDTO)

            .rating(reviewDTO)
            .related_products(relatedDTO)
            .build();
    }

    private List<QueryProductRelatedDTO> loadRelatedDTO(ProductId id, List<QueryProductCategoryDTO> categoryDTO) {
        final var categoryIdList = categoryDTO.stream()
            .map(QueryProductCategoryDTO::id)
            .distinct()
            .toList();

        return factory
            .select(
                new QQueryProductDetailAdapter_RelatedProductDTO(
                    PRODUCT.id,
                    PRODUCT.name,
                    PRODUCT.slug,
                    PRODUCT.shortDescription,

                    IMAGE.url,
                    IMAGE.altText,

                    PRICE.basePrice,
                    PRICE.salePrice,
                    PRICE.currency
                )
            )
            .distinct()
            .from(PRODUCT)

            .join(PRODUCT_CATEGORY)
            .on(
                PRODUCT_CATEGORY.category.id.in(categoryIdList)
                    .and(PRODUCT_CATEGORY.product.eq(PRODUCT))
                    .and(PRODUCT_CATEGORY.product.id.ne(id.value()))
            )

            .leftJoin(IMAGE)
            .on(
                IMAGE.product.eq(PRODUCT)
                    .and(IMAGE.isPrimary.isTrue())
            )

            .leftJoin(PRICE)
            .on(PRICE.product.eq(PRODUCT))

            .fetch()
            .stream()
            .map(i -> QueryProductRelatedDTO.builder()
                .id(i.id)
                .name(i.name)
                .slug(i.slug)
                .short_description(i.shortDescription)
                .primary_image(
                    QueryProductRelatedDTO.Images.builder()
                        .url(i.url)
                        .alt_text(i.altText)
                        .build()
                )
                .base_price(i.basePrice)
                .sale_price(i.salePrice)
                .currency(i.currency)
                .build())
            .toList();
    }

    private static QueryProductSellerDTO convertSellerDTO(ProductDTO result) {
        return QueryProductSellerDTO.builder()
            .id(result.sellerId)
            .name(result.sellerName)
            .description(result.sellerDescription)
            .logo_url(result.sellerLogoUrl)
            .rating(result.rating)
            .contact_email(result.contactEmail)
            .contact_phone(result.contactPhone)
            .build();
    }

    private static QueryProductBrandDTO convertBrandDTO(ProductDTO result) {
        return QueryProductBrandDTO.builder()
            .id(result.brandId)
            .name(result.brandName)
            .description(result.brandDescription)
            .logo_url(result.brandLogoUrl)
            .website(result.website)
            .build();
    }

    private static QueryProductPriceDTO convertPriceDTO(ProductDTO result) {
        return QueryProductPriceDTO.builder()
            .base_price(result.basePrice)
            .sale_price(result.salePrice)
            .currency(result.currency)
            .tax_rate(result.taxRate)
            .build();
    }

    private static QueryProductDetailDTO convertDetailDTO(ProductDTO result) {
        return QueryProductDetailDTO.builder()
            .weight(result.weight)
            .dimensions(result.dimensions)
            .materials(result.materials)
            .country_of_origin(result.countryOfOrigin)
            .warranty_info(result.warrantyInfo)
            .care_instructions(result.careInstructions)
            .additional_info(result.additionalInfo)
            .build();
    }

    private QueryProductRatingDTO loadProductReview(ProductId id) {
        final var reviewResult = factory
            .selectFrom(REVIEW)
            .where(REVIEW.product.id.eq(id.value()))

            .fetch();

        return QueryProductRatingDTO.builder()
            .average(
                reviewResult.stream()
                    .mapToInt(ReviewJpaEntity::getRating)
                    .average()
                    .orElse(0)
            )
            .count(reviewResult.size())
            .distribution(
                reviewResult.stream()
                    .collect(Collectors.groupingBy(
                        e -> e.getRating().toString(),
                        Collectors.counting()
                    ))
            )
            .build();
    }

    private List<QueryProductTagDTO> loadProductTag(ProductId id) {
        return factory
            .select(
                new QQueryProductDetailAdapter_TagDTO(
                    TAG.id,
                    TAG.name,
                    TAG.slug
                )
            )
            .from(PRODUCT_TAG)
            .where(PRODUCT_TAG.product.id.eq(id.value()))

            .leftJoin(TAG)
            .on(TAG.eq(PRODUCT_TAG.tag))

            .fetch()
            .stream()
            .map(e ->
                QueryProductTagDTO.builder()
                    .id(e.id)
                    .name(e.name)
                    .slug(e.slug)
                    .build())
            .toList();
    }

    private List<QueryProductImageDTO> loadProductImage(ProductId id) {
        return factory
            .select(
                new QQueryProductDetailAdapter_ImageDTO(
                    IMAGE.id,
                    IMAGE.url,
                    IMAGE.altText,
                    IMAGE.isPrimary,
                    IMAGE.displayOrder,
                    IMAGE.option.id
                )
            )
            .from(IMAGE)
            .where(IMAGE.product.id.eq(id.value()))
            .orderBy(IMAGE.id.asc())
            .fetch()
            .stream()
            .map(e -> QueryProductImageDTO.builder()
                .id(e.id)
                .url(e.url)
                .alt_text(e.altText)
                .is_primary(e.isPrimary)
                .display_order(e.displayOrder)
                .option_id(e.optionId)
                .build())
            .toList();
    }

    private List<QueryProductOptionGroup> loadProductOption(ProductId id) {
        return factory
            .select(
                new QQueryProductDetailAdapter_OptionGroupDTO(
                    PRODUCT_OPTION_GROUP,
                    PRODUCT_OPTION
                )
            )
            .from(PRODUCT_OPTION_GROUP)
            .where(PRODUCT_OPTION_GROUP.product.id.eq(id.value()))

            .leftJoin(OPTION)
            .on(OPTION.optionGroup.eq(PRODUCT_OPTION_GROUP))

            .leftJoin(PRODUCT_OPTION)
            .on(PRODUCT_OPTION.optionGroup.eq(PRODUCT_OPTION_GROUP))

            .fetch().stream()
            .collect(Collectors.groupingBy(
                e -> e.group,
                LinkedHashMap::new,
                Collectors.mapping(i -> i.option, Collectors.toList())
            ))
            .entrySet()
            .stream()
            .map(e -> {
                final var key = e.getKey();
                final var value = e.getValue();
                return QueryProductOptionGroup.builder()
                    .id(key.getId())
                    .name(key.getName())
                    .display_order(key.getDisplayOrder())
                    .options(
                        value.stream()
                            .map(i ->
                                QueryProductOptionGroup.Options.builder()
                                    .id(i.getId())
                                    .name(i.getName())
                                    .additional_price(i.getAdditionalPrice())
                                    .sku(i.getSku())
                                    .stock(i.getStock())
                                    .display_order(i.getDisplayOrder())
                                    .build()
                            )
                            .toList()
                    )
                    .build();
            })
            .toList();
    }

    private List<QueryProductCategoryDTO> loadProductCategory(ProductId id) {
        return factory
            .select(
                new QQueryProductDetailAdapter_CategoryDTO(
                    CATEGORY.id,
                    CATEGORY.name,
                    CATEGORY.slug,
                    PRODUCT_CATEGORY.isPrimary,

                    new QQueryProductDetailAdapter_CategoryDTO_Parent(
                        PARENT_CATEGORY.id,
                        PARENT_CATEGORY.name,
                        PARENT_CATEGORY.slug
                    )
                )
            )
            .from(PRODUCT_CATEGORY)
            .where(PRODUCT_CATEGORY.product.id.eq(id.value()))

            .join(CATEGORY)
            .on(CATEGORY.eq(PRODUCT_CATEGORY.category))

            .leftJoin(PARENT_CATEGORY)
            .on(PARENT_CATEGORY.eq(CATEGORY.parent))

            .fetch()
            .stream()
            .map(e ->
                QueryProductCategoryDTO.builder()
                    .id(e.id)
                    .name(e.name)
                    .slug(e.slug)
                    .is_primary(e.isPrimary)
                    .parent(
                        QueryProductCategoryDTO.ParentDTO.builder()
                            .id(e.parent.id)
                            .name(e.parent.name)
                            .slug(e.parent.slug)
                            .build()
                    )
                    .build()
            )
            .toList();
    }

    private ProductDTO loadProductDetail(ProductId id) {
        return Objects.requireNonNull(factory
            .select(
                new QQueryProductDetailAdapter_ProductDTO(
                    PRODUCT.id.as("id"),
                    PRODUCT.name.as("name"),
                    PRODUCT.slug.as("slug"),
                    PRODUCT.shortDescription.as("shortDescription"),
                    PRODUCT.fullDescription.as("fullDescription"),

                    SELLER.id.as("sellerId"),
                    SELLER.name.as("sellerName"),
                    SELLER.description.as("sellerDescription"),
                    SELLER.logoUrl.as("sellerLogoUrl"),
                    SELLER.rating.as("rating"),
                    SELLER.contactEmail.as("contactEmail"),
                    SELLER.contactPhone.as("contactPhone"),

                    BRAND.id.as("brandId"),
                    BRAND.name.as("brandName"),
                    BRAND.description.as("brandDescription"),
                    BRAND.logoUrl.as("brandLogoUrl"),
                    BRAND.website.as("website"),

                    PRODUCT.status.as("status"),
                    PRODUCT.createdAt.as("createdAt"),
                    PRODUCT.updatedAt.as("updatedAt"),

                    DETAIL.weight.as("weight"),
                    DETAIL.dimensions.as("dimensions"),
                    DETAIL.materials.as("materials"),
                    DETAIL.countryOfOrigin.as("countryOfOrigin"),
                    DETAIL.warrantyInfo.as("warrantyInfo"),
                    DETAIL.careInstructions.as("careInstructions"),
                    DETAIL.additionalInfo.as("additionalInfo"),

                    PRICE.basePrice.as("basePrice"),
                    PRICE.salePrice.as("salePrice"),
                    PRICE.currency.as("currency"),
                    PRICE.taxRate.as("taxRate")
                )
            )
            .from(PRODUCT)
            .where(PRODUCT.id.eq(id.value()))

            .leftJoin(SELLER)
            .on(SELLER.eq(PRODUCT.seller))

            .leftJoin(BRAND)
            .on(BRAND.eq(PRODUCT.brand))

            .leftJoin(DETAIL)
            .on(DETAIL.product.eq(PRODUCT))

            .leftJoin(PRICE)
            .on(PRICE.product.eq(PRODUCT))

            .fetchOne()
        );
    }

    public static class ProductDTO {
        private final Long id;
        private final String name;
        private final String slug;
        private final String shortDescription;
        private final String fullDescription;

        private final Long sellerId;
        private final String sellerName;
        private final String sellerDescription;
        private final String sellerLogoUrl;
        private final BigDecimal rating;
        private final String contactEmail;
        private final String contactPhone;

        private final Long brandId;
        private final String brandName;
        private final String brandDescription;
        private final String brandLogoUrl;
        private final String website;

        private final ProductStatus status;
        private final LocalDateTime createdAt;
        private final LocalDateTime updatedAt;

        private final BigDecimal weight;
        private final Map<String, Object> dimensions;
        private final String materials;
        private final String countryOfOrigin;
        private final String warrantyInfo;
        private final String careInstructions;
        private final Map<String, Object> additionalInfo;

        private final BigDecimal basePrice;
        private final BigDecimal salePrice;
        private final String currency;
        private final BigDecimal taxRate;

        @QueryProjection
        public ProductDTO(Long id, String name, String slug, String shortDescription, String fullDescription, Long sellerId, String sellerName, String sellerDescription, String sellerLogoUrl, BigDecimal rating, String contactEmail, String contactPhone, Long brandId, String brandName, String brandDescription, String brandLogoUrl, String website, ProductStatus status, LocalDateTime createdAt, LocalDateTime updatedAt, BigDecimal weight, Map<String, Object> dimensions, String materials, String countryOfOrigin, String warrantyInfo, String careInstructions, Map<String, Object> additionalInfo, BigDecimal basePrice, BigDecimal salePrice, String currency, BigDecimal taxRate) {
            this.id = id;
            this.name = name;
            this.slug = slug;
            this.shortDescription = shortDescription;
            this.fullDescription = fullDescription;
            this.sellerId = sellerId;
            this.sellerName = sellerName;
            this.sellerDescription = sellerDescription;
            this.sellerLogoUrl = sellerLogoUrl;
            this.rating = rating;
            this.contactEmail = contactEmail;
            this.contactPhone = contactPhone;
            this.brandId = brandId;
            this.brandName = brandName;
            this.brandDescription = brandDescription;
            this.brandLogoUrl = brandLogoUrl;
            this.website = website;
            this.status = status;
            this.createdAt = createdAt;
            this.updatedAt = updatedAt;
            this.weight = weight;
            this.dimensions = dimensions;
            this.materials = materials;
            this.countryOfOrigin = countryOfOrigin;
            this.warrantyInfo = warrantyInfo;
            this.careInstructions = careInstructions;
            this.additionalInfo = additionalInfo;
            this.basePrice = basePrice;
            this.salePrice = salePrice;
            this.currency = currency;
            this.taxRate = taxRate;
        }
    }

    public static class CategoryDTO {
        private final Long id;
        private final String name;
        private final String slug;
        private final Boolean isPrimary;
        private final Parent parent;

        @QueryProjection
        public CategoryDTO(Long id, String name, String slug, Boolean isPrimary, Parent parent) {
            this.id = id;
            this.name = name;
            this.slug = slug;
            this.isPrimary = isPrimary;
            this.parent = parent;
        }

        public static class Parent {
            private final Long id;
            private final String name;
            private final String slug;

            @QueryProjection
            public Parent(Long id, String name, String slug) {
                this.id = id;
                this.name = name;
                this.slug = slug;
            }
        }
    }

    public static class OptionGroupDTO {
        private final ProductOptionGroupJpaEntity group;
        private final ProductOptionJpaEntity option;

        @QueryProjection
        public OptionGroupDTO(ProductOptionGroupJpaEntity group, ProductOptionJpaEntity option) {
            this.group = group;
            this.option = option;
        }
    }

    public static class ImageDTO {
        private final Long id;
        private final String url;
        private final String altText;
        private final Boolean isPrimary;
        private final Integer displayOrder;
        private final Long optionId;

        @QueryProjection
        public ImageDTO(Long id, String url, String altText, Boolean isPrimary, Integer displayOrder, Long optionId) {
            this.id = id;
            this.url = url;
            this.altText = altText;
            this.isPrimary = isPrimary;
            this.displayOrder = displayOrder;
            this.optionId = optionId;
        }
    }

    public static class TagDTO {
        private final Long id;
        private final String name;
        private final String slug;

        @QueryProjection
        public TagDTO(Long id, String name, String slug) {
            this.id = id;
            this.name = name;
            this.slug = slug;
        }
    }

    public static class RelatedProductDTO {
        private final Long id;
        private final String name;
        private final String slug;
        private final String shortDescription;
        private final String url;
        private final String altText;
        private final BigDecimal basePrice;
        private final BigDecimal salePrice;
        private final String currency;

        @QueryProjection
        public RelatedProductDTO(Long id, String name, String slug, String shortDescription, String url, String altText, BigDecimal basePrice, BigDecimal salePrice, String currency) {
            this.id = id;
            this.name = name;
            this.slug = slug;
            this.shortDescription = shortDescription;
            this.url = url;
            this.altText = altText;
            this.basePrice = basePrice;
            this.salePrice = salePrice;
            this.currency = currency;
        }
    }


    @Override
    public QProductJpaEntity getQClazz() {
        return PRODUCT;
    }

    @Override
    protected Function<String, Expression<? extends Comparable<?>>> sortingCondition() {
        return value -> switch (value.toUpperCase()) {

            default -> null;
        };
    }

    @Override
    @SuppressWarnings("unchecked")
    protected Function<ParamCondition<?>, BooleanExpression> paramFilterCondition() {
        return property -> switch (property.key()) {

            default -> null;
        };
    }
}