package investLee.platform.ecommerce.repository.custom;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import investLee.platform.ecommerce.domain.ProductStatus;
import investLee.platform.ecommerce.domain.entity.*;
import investLee.platform.ecommerce.dto.request.ProductSearchConditionRequest;
import investLee.platform.ecommerce.dto.request.ProductSearchRequest;
import investLee.platform.ecommerce.dto.ProductSortType;
import investLee.platform.ecommerce.dto.response.ProductSummaryResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@Repository
public class ProductRepositoryImpl implements ProductRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public ProductRepositoryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public Page<ProductSummaryResponse> searchProducts(ProductSearchConditionRequest condition) {
        QProductEntity product = QProductEntity.productEntity;
        QProductPriceEntity price = QProductPriceEntity.productPriceEntity;
        QBrandEntity brand = QBrandEntity.brandEntity;
        QReviewEntity review = QReviewEntity.reviewEntity;

        JPAQuery<ProductSummaryResponse> query = queryFactory
                .select(Projections.constructor(ProductSummaryResponse.class,
                        product.id,
                        product.name,
                        product.slug,
                        price.basePrice,
                        price.salePrice,
                        Expressions.stringTemplate(
                                "(select pi.url from ProductImageEntity pi where pi.product.id = {0} and pi.isPrimary = true limit 1)",
                                product.id),
                        brand.name,
                        review.rating.avg()
                ))
                .from(product)
                .leftJoin(price).on(price.product.eq(product))
                .leftJoin(brand).on(brand.eq(product.brand))
                .leftJoin(review).on(review.product.eq(product))
                .where(buildWhereClause(condition))
                .groupBy(product.id, price.id, brand.name)
                .orderBy(applySort(condition.getSortType()));

        long total = query.fetch().size();

        List<ProductSummaryResponse> content = query
                .offset(condition.getPage() * condition.getSize())
                .limit(condition.getSize())
                .fetch();

        return new PageImpl<>(content, PageRequest.of(condition.getPage(), condition.getSize()), total);
    }

    private BooleanBuilder buildWhereClause(ProductSearchConditionRequest condition) {
        QProductEntity product = QProductEntity.productEntity;
        QProductPriceEntity price = QProductPriceEntity.productPriceEntity;

        BooleanBuilder builder = new BooleanBuilder();

        builder.and(product.status.ne(ProductStatus.DELETED));

        if (condition.getCategoryId() != null) {
            builder.and(product.productCategories.any().category.id.eq(condition.getCategoryId()));
        }

        if (condition.getBrandId() != null) {
            builder.and(product.brand.id.eq(condition.getBrandId()));
        }

        if (condition.getSellerId() != null) {
            builder.and(product.seller.id.eq(condition.getSellerId()));
        }

        if (condition.getMinPrice() != null) {
            builder.and(price.basePrice.goe(condition.getMinPrice()));
        }

        if (condition.getMaxPrice() != null) {
            builder.and(price.basePrice.loe(condition.getMaxPrice()));
        }

        if (condition.getInStockOnly() != null && condition.getInStockOnly()) {
            builder.and(product.optionGroups.any().options.any().stock.gt(0));
        }

        if (StringUtils.hasText(condition.getKeyword())) {
            builder.and(product.name.containsIgnoreCase(condition.getKeyword())
                    .or(product.fullDescription.containsIgnoreCase(condition.getKeyword()))
                    .or(product.brand.name.containsIgnoreCase(condition.getKeyword())));
        }

        if (condition.getFromDate() != null && condition.getToDate() != null) {
            builder.and(product.createdAt.between(
                    condition.getFromDate().atStartOfDay(),
                    condition.getToDate().plusDays(1).atStartOfDay()));
        } else if (condition.getFromDate() != null) {
            builder.and(product.createdAt.goe(condition.getFromDate().atStartOfDay()));
        } else if (condition.getToDate() != null) {
            builder.and(product.createdAt.lt(condition.getToDate().plusDays(1).atStartOfDay()));
        }

        return builder;
    }

    private OrderSpecifier<?> applySort(ProductSortType sortType) {
        QProductEntity product = QProductEntity.productEntity;
        QProductPriceEntity price = QProductPriceEntity.productPriceEntity;
        QReviewEntity review = QReviewEntity.reviewEntity;

        return switch (sortType) {
            case PRICE_ASC -> price.salePrice.asc();
            case PRICE_DESC -> price.salePrice.desc();
            case RATING_DESC -> review.rating.avg().desc();
            default -> product.createdAt.desc();
        };
    }

    @Override
    public Page<ProductSummaryResponse> searchByKeyword(ProductSearchRequest dto) {
        QProductEntity product = QProductEntity.productEntity;
        QBrandEntity brand = QBrandEntity.brandEntity;
        QProductPriceEntity price = QProductPriceEntity.productPriceEntity;
        QReviewEntity review = QReviewEntity.reviewEntity;
        QProductTagEntity productTag = QProductTagEntity.productTagEntity;
        QTagEntity tag = QTagEntity.tagEntity;
        QProductCategoryEntity productCategory = QProductCategoryEntity.productCategoryEntity;
        QCategoryEntity category = QCategoryEntity.categoryEntity;

        BooleanBuilder builder = new BooleanBuilder();
        String keyword = dto.getKeyword().toLowerCase();

        builder.and(
                product.name.lower().contains(keyword)
                        .or(product.fullDescription.lower().contains(keyword))
                        .or(brand.name.lower().contains(keyword))
                        .or(tag.name.lower().contains(keyword))
                        .or(category.name.lower().contains(keyword))
        );

        JPAQuery<ProductSummaryResponse> query = queryFactory
                .select(Projections.constructor(ProductSummaryResponse.class,
                        product.id,
                        product.name,
                        product.slug,
                        price.basePrice,
                        price.salePrice,
                        Expressions.stringTemplate(
                                "(select pi.url from ProductImageEntity pi where pi.product.id = {0} and pi.isPrimary = true limit 1)",
                                product.id),
                        brand.name,
                        review.rating.avg()
                ))
                .from(product)
                .leftJoin(product.brand, brand)
                .leftJoin(price).on(price.product.eq(product))
                .leftJoin(QReviewEntity.reviewEntity).on(review.product.eq(product))
                .leftJoin(QProductTagEntity.productTagEntity).on(productTag.product.eq(product))
                .leftJoin(productTag.tag, tag)
                .leftJoin(product.productCategories, productCategory)
                .leftJoin(productCategory.category, category)
                .where(builder)
                .groupBy(product.id, price.id, brand.name)
                .orderBy(applySort(dto.getSortType()));

        long total = query.fetch().size();

        List<ProductSummaryResponse> result = query
                .offset(dto.getPage() * dto.getSize())
                .limit(dto.getSize())
                .fetch();

        return new PageImpl<>(result, PageRequest.of(dto.getPage(), dto.getSize()), total);
    }

    private BooleanExpression betweenCreatedAt(LocalDate from, LocalDate to) {
        QProductEntity product = QProductEntity.productEntity;

        if (from != null && to != null) {
            return product.createdAt.between(from.atStartOfDay(), to.plusDays(1).atStartOfDay());
        } else if (from != null) {
            return product.createdAt.goe(from.atStartOfDay());
        } else if (to != null) {
            return product.createdAt.lt(to.plusDays(1).atStartOfDay());
        }
        return null;
    }

    @Override
    public List<ProductSummaryResponse> findRelatedProducts(Long productId, int limit) {
        QProductEntity product = QProductEntity.productEntity;
        QProductCategoryEntity pc = QProductCategoryEntity.productCategoryEntity;
        QCategoryEntity category = QCategoryEntity.categoryEntity;
        QProductPriceEntity price = QProductPriceEntity.productPriceEntity;
        QBrandEntity brand = QBrandEntity.brandEntity;
        QReviewEntity review = QReviewEntity.reviewEntity;

        // 1. 현재 상품이 속한 카테고리 ID 추출
        List<Long> categoryIds = queryFactory
                .select(pc.category.id)
                .from(pc)
                .where(pc.product.id.eq(productId))
                .fetch();

        if (categoryIds.isEmpty()) return Collections.emptyList();

        // 2. 같은 카테고리에 속한 다른 상품 추천
        return queryFactory
                .select(Projections.constructor(ProductSummaryResponse.class,
                        product.id,
                        product.name,
                        product.slug,
                        price.basePrice,
                        price.salePrice,
                        Expressions.stringTemplate(
                                "(select pi.url from ProductImageEntity pi where pi.product.id = {0} and pi.isPrimary = true limit 1)",
                                product.id),
                        brand.name,
                        review.rating.avg()
                ))
                .from(product)
                .leftJoin(price).on(price.product.eq(product))
                .leftJoin(product.productCategories, pc)
                .leftJoin(pc.category, category)
                .leftJoin(product.brand, brand)
                .leftJoin(review).on(review.product.eq(product))
                .where(
                        category.id.in(categoryIds),
                        product.id.ne(productId) // 현재 상품 제외
                )
                .groupBy(product.id, price.id, brand.name)
                .orderBy(review.rating.avg().desc().nullsLast()) // 평점 순 or 랜덤도 가능
                .limit(limit)
                .fetch();
    }
}