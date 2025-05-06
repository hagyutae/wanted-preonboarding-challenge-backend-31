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
import lkdcode.wanted.ecommerce.modules.products.application.ports.out.query.QueryProductListOutPort;
import lkdcode.wanted.ecommerce.modules.products.application.usecase.query.dto.QueryProductBrandDTO;
import lkdcode.wanted.ecommerce.modules.products.application.usecase.query.dto.QueryProductSellerDTO;
import lkdcode.wanted.ecommerce.modules.products.application.usecase.query.dto.list.QueryPrimaryImage;
import lkdcode.wanted.ecommerce.modules.products.application.usecase.query.dto.list.QueryProductDTO;
import lkdcode.wanted.ecommerce.modules.products.application.usecase.query.dto.list.QueryProductListResult;
import lkdcode.wanted.ecommerce.modules.products.application.usecase.query.value.Pagination;
import lkdcode.wanted.ecommerce.modules.products.application.usecase.query.value.ParamCondition;
import lkdcode.wanted.ecommerce.modules.products.application.usecase.query.value.QueryParamConditions;
import lkdcode.wanted.ecommerce.modules.products.domain.value.ProductStatus;
import lkdcode.wanted.ecommerce.modules.reviews.adapter.infrastructure.jpa.entity.QReviewJpaEntity;
import lkdcode.wanted.ecommerce.modules.sellers.adapter.infrastructure.jpa.entity.QSellerJpaEntity;
import lombok.Getter;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Function;

@Service
public class QueryProductListAdapter extends QueryBase<ProductJpaEntity, QProductJpaEntity> implements QueryProductListOutPort {
    private static final QProductJpaEntity PRODUCT = QProductJpaEntity.productJpaEntity;
    private static final QProductPriceJpaEntity PRICE = QProductPriceJpaEntity.productPriceJpaEntity;
    private static final QProductOptionGroupJpaEntity PRODUCT_OPTION_GROUP = QProductOptionGroupJpaEntity.productOptionGroupJpaEntity;
    private static final QProductOptionJpaEntity PRODUCT_OPTION = QProductOptionJpaEntity.productOptionJpaEntity;
    private static final QProductImageJpaEntity IMAGE = QProductImageJpaEntity.productImageJpaEntity;

    private static final QBrandJpaEntity BRAND = QBrandJpaEntity.brandJpaEntity;
    private static final QSellerJpaEntity SELLER = QSellerJpaEntity.sellerJpaEntity;
    private static final QReviewJpaEntity REVIEW = QReviewJpaEntity.reviewJpaEntity;
    private static final QCategoryJpaEntity CATEGORY = QCategoryJpaEntity.categoryJpaEntity;

    public QueryProductListAdapter(JPAQueryFactory factory, QueryDslUtil queryDslUtil) {
        super(factory, queryDslUtil);
    }

    @Override
    public QueryProductListResult loadList(Pageable pageable, QueryParamConditions queryParamConditions) {
        final var result = factory
            .select(
                new QQueryProductListAdapter_LoadListDTO(
                    PRODUCT.id.as("id"),
                    PRODUCT.name.as("name"),
                    PRODUCT.slug.as("slug"),
                    PRODUCT.shortDescription.as("shortDescription"),

                    PRICE.basePrice.as("basePrice"),
                    PRICE.salePrice.as("salePrice"),
                    PRICE.currency.as("currency"),

                    IMAGE.url.as("url"),
                    IMAGE.altText.as("altText"),

                    BRAND.id.as("brandId"),
                    BRAND.name.as("brandName"),

                    SELLER.id.as("sellerId"),
                    SELLER.name.as("sellerName"),

                    REVIEW.rating.avg().as("rating"),
                    REVIEW.count().intValue().as("reviewCount"),
                    PRODUCT_OPTION.stock.sum().as("stock"),
                    PRODUCT.status.as("status"),
                    PRODUCT.createdAt.as("createdAt")
                )
            )

            .from(PRODUCT)
            .where(usingWhereCondition(queryParamConditions, PRODUCT.status.ne(ProductStatus.DELETED)))

            .join(PRICE)
            .on(PRICE.product.eq(PRODUCT))

            .leftJoin(IMAGE)
            .on(
                IMAGE.product.eq(PRODUCT)
                    .and(IMAGE.isPrimary.isTrue())
            )

            .leftJoin(BRAND)
            .on(BRAND.eq(PRODUCT.brand))

            .leftJoin(SELLER)
            .on(SELLER.eq(PRODUCT.seller))

            .leftJoin(REVIEW)
            .on(REVIEW.product.eq(PRODUCT))

            .leftJoin(PRODUCT_OPTION_GROUP)
            .on(PRODUCT_OPTION_GROUP.product.eq(PRODUCT))

            .leftJoin(PRODUCT_OPTION)
            .on(PRODUCT_OPTION.optionGroup.eq(PRODUCT_OPTION_GROUP))

            .orderBy(queryDslUtil.createOrderSpecifier(pageable, sortingCondition()))
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())

            .groupBy(
                PRODUCT.id,
                PRODUCT.name,
                PRODUCT.slug,
                PRODUCT.shortDescription,

                PRICE.basePrice,
                PRICE.salePrice,
                PRICE.currency,

                IMAGE.url,
                IMAGE.altText,

                BRAND.id,
                BRAND.name,

                SELLER.id,
                SELLER.name,

                PRODUCT.status,
                PRODUCT.createdAt
            )

            .fetch();

        final var page = new PageImpl<>(result, pageable, getTotalSize(usingWhereCondition(queryParamConditions)));

        final var pagination = Pagination.builder()
            .totalItems(page.getTotalElements())
            .totalPages(page.getTotalPages())
            .currentPage(page.getNumber() + 1)
            .size(page.getSize())
            .build();

        return QueryProductListResult.builder()
            .items(result.stream()
                .map(e -> QueryProductDTO.builder()
                    .name(e.name)
                    .slug(e.slug)
                    .shortDescription(e.shortDescription)
                    .basePrice(e.basePrice)
                    .salePrice(e.salePrice)
                    .currency(e.currency)
                    .primary_image(
                        QueryPrimaryImage.builder()
                            .url(e.url)
                            .alt_text(e.altText)
                            .build()
                    )
                    .brand(
                        QueryProductBrandDTO.builder()
                            .id(e.brandId)
                            .name(e.brandName)
                            .build()
                    )
                    .seller(
                        QueryProductSellerDTO.builder()
                            .id(e.sellerId)
                            .name(e.sellerName)
                            .build()
                    )
                    .rating(e.rating)
                    .review_count(e.reviewCount)
                    .in_stock(e.inStock)
                    .status(e.status)
                    .created_at(e.createdAt)
                    .build()
                )
                .toList())
            .pagination(pagination)
            .build();
    }

    @Getter
    public static class LoadListDTO {
        private final Long id;
        private final String name;
        private final String slug;
        private final String shortDescription;

        private final BigDecimal basePrice;
        private final BigDecimal salePrice;
        private final String currency;

        private final String url;
        private final String altText;

        private final Long brandId;
        private final String brandName;

        private final Long sellerId;
        private final String sellerName;

        private final Double rating;
        private final int reviewCount;
        private final boolean inStock;
        private final ProductStatus status;
        private final LocalDateTime createdAt;

        @QueryProjection
        public LoadListDTO(Long id, String name, String slug, String shortDescription, BigDecimal basePrice, BigDecimal salePrice, String currency, String url, String altText, Long brandId, String brandName, Long sellerId, String sellerName, Double rating, int reviewCount, int stock, ProductStatus status, LocalDateTime createdAt) {
            this.id = id;
            this.name = name;
            this.slug = slug;
            this.shortDescription = shortDescription;
            this.basePrice = basePrice;
            this.salePrice = salePrice;
            this.currency = currency;
            this.url = url;
            this.altText = altText;
            this.brandId = brandId;
            this.brandName = brandName;
            this.sellerId = sellerId;
            this.sellerName = sellerName;
            this.rating = rating;
            this.reviewCount = reviewCount;
            this.inStock = stock > 0;
            this.status = status;
            this.createdAt = createdAt;
        }
    }

    @Override
    public QProductJpaEntity getQClazz() {
        return PRODUCT;
    }

    @Override
    protected Function<String, Expression<? extends Comparable<?>>> sortingCondition() {
        return value -> switch (value.toUpperCase()) {
            case "NAME" -> PRODUCT.name;
            case "SLUG" -> PRODUCT.slug;
            case "SHORT_DESCRIPTION" -> PRODUCT.shortDescription;
            case "FULL_DESCRIPTION" -> PRODUCT.fullDescription;
            case "UPDATED_AT" -> PRODUCT.updatedAt;
            case "STATUS" -> PRODUCT.status;

            default -> PRODUCT.createdAt;
        };
    }

    @Override
    @SuppressWarnings("unchecked")
    protected Function<ParamCondition<?>, BooleanExpression> paramFilterCondition() {
        return property -> switch (property.key()) {
            case "status" -> PRODUCT.status.eq(ProductStatus.valueOf(property.valueAsStringToUpperCase()));
            case "minPrice" -> PRICE.basePrice.goe(
                BigDecimal.valueOf(
                    Double.parseDouble(property.valueAsStringToUpperCase())
                )
            );
            case "maxPrice" -> PRICE.basePrice.loe(
                BigDecimal.valueOf(
                    Double.parseDouble(property.valueAsStringToUpperCase())
                )
            );

            case "category" -> CATEGORY.id.in((List<Long>) property.value());
            case "seller" -> SELLER.id.eq(Long.parseLong(property.valueAsStringToUpperCase()));
            case "brand" -> BRAND.id.eq(Long.parseLong(property.valueAsStringToUpperCase()));
            case "inStock" -> (Boolean) property.value() ? PRODUCT_OPTION.stock.goe(1) : PRODUCT_OPTION.stock.lt(1);
            case "search" -> PRODUCT.name.containsIgnoreCase(property.valueAsStringToUpperCase());

            default -> null;
        };
    }
}