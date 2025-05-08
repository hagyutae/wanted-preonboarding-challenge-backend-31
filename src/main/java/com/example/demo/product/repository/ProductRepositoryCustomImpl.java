package com.example.demo.product.repository;

import com.example.demo.product.dto.ProductQueryFilter;
import com.example.demo.product.entity.ProductEntity;
import com.example.demo.product.entity.ProductStatus;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static com.example.demo.brand.entity.QBrandEntity.brandEntity;
import static com.example.demo.product.entity.QProductCategoryEntity.productCategoryEntity;
import static com.example.demo.product.entity.QProductDetailEntity.productDetailEntity;
import static com.example.demo.product.entity.QProductEntity.productEntity;
import static com.example.demo.product.entity.QProductPriceEntity.productPriceEntity;
import static com.example.demo.productoption.entity.QProductOptionEntity.productOptionEntity;
import static com.example.demo.user.entity.QSellerEntity.sellerEntity;
import static java.util.Objects.nonNull;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryCustomImpl implements ProductRepositoryCustom {
    private final JPAQueryFactory qf;

    @Override
    public Page<ProductEntity> findProductSummaryPage(ProductQueryFilter productQueryFilter, Pageable pageable) {
        Long total = getTotalCountQuery(productQueryFilter);

        if (total == null || total == 0) {
            return new PageImpl<>(Collections.emptyList(), pageable, 0);
        }

        List<ProductEntity> contents = getContentQuery(productQueryFilter, pageable)
                .fetch();

        return new PageImpl<>(contents, pageable, total);
    }

    @Override
    public Boolean existBySlug(String slug) {
        return qf.selectOne()
                .from(productEntity)
                .where(productEntity.slug.eq(slug))
                .fetchFirst() != null;
    }

    @Override
    public List<ProductEntity> findTop10LatestList() {
        return qf.select(productEntity)
                .from(productEntity)
                .leftJoin(productEntity.productPriceEntity, productPriceEntity).fetchJoin()
                .leftJoin(productEntity.productDetailEntity, productDetailEntity).fetchJoin()
                .leftJoin(productEntity.sellerEntity, sellerEntity).fetchJoin()
                .leftJoin(productEntity.brandEntity, brandEntity).fetchJoin()
                .limit(10)
                .orderBy(productEntity.createdAt.desc())
                .fetch();
    }

    @Override
    public List<ProductEntity> findAllByIds(Collection<Long> ids) {
        return qf.select(productEntity)
                .from(productEntity)
                .leftJoin(productEntity.productPriceEntity, productPriceEntity).fetchJoin()
                .leftJoin(productEntity.productDetailEntity, productDetailEntity).fetchJoin()
                .leftJoin(productEntity.sellerEntity, sellerEntity).fetchJoin()
                .leftJoin(productEntity.brandEntity, brandEntity).fetchJoin()
                .where(productEntity.id.in(ids))
                .fetch();
    }


    private Long getTotalCountQuery(ProductQueryFilter productQueryFilter) {
        JPAQuery<Long> countQuery = qf.select(productEntity.count())
                .from(productEntity);

        applyJoinConditions(countQuery, productQueryFilter);

        return countQuery
                .where(this.satisfyProductQueryFilter(productQueryFilter))
                .fetchOne();
    }

    private JPAQuery<ProductEntity> getContentQuery(ProductQueryFilter productQueryFilter, Pageable pageable) {
        JPAQuery<ProductEntity> contentQuery = qf.select(productEntity)
                .from(productEntity)
                .leftJoin(productEntity.productPriceEntity, productPriceEntity).fetchJoin()
                .leftJoin(productEntity.productDetailEntity, productDetailEntity).fetchJoin()
                .leftJoin(productEntity.sellerEntity, sellerEntity).fetchJoin()
                .leftJoin(productEntity.brandEntity, brandEntity).fetchJoin();

        applyJoinConditions(contentQuery, productQueryFilter);

        List<OrderSpecifier<?>> orderSpecifiers = getOrderSpecifiers(pageable.getSort());
        contentQuery.orderBy(orderSpecifiers.toArray(new OrderSpecifier[0]));

        return contentQuery
                .where(this.satisfyProductQueryFilter(productQueryFilter))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());
    }

    private List<OrderSpecifier<?>> getOrderSpecifiers(Sort sort) {
        PathBuilder<?> entityPath = new PathBuilder<>((Class<?>) ProductEntity.class, "productEntity");
        List<OrderSpecifier<?>> orders = new ArrayList<>();

        for (Sort.Order order : sort) {
            Order direction = order.isAscending() ? Order.ASC : Order.DESC;
            String property = order.getProperty();

            if ("price".equals(property)) {
                orders.add(new OrderSpecifier<>(direction, entityPath.getNumber(property, BigDecimal.class)));
            } else if ("created_at".equals(property)) {
                orders.add(new OrderSpecifier<>(direction, entityPath.getDateTime("createdAt", LocalDateTime.class)));
            } else {
                orders.add(new OrderSpecifier<>(direction, entityPath.getString(property)));
            }
        }

        return orders;
    }


    private void applyJoinConditions(JPAQuery<?> query, ProductQueryFilter productQueryFilter) {
        if (nonNull(productQueryFilter.categoryIds()) && !productQueryFilter.categoryIds().isEmpty()) {
            query.join(productCategoryEntity).on(productCategoryEntity.productEntity.id.eq(productEntity.id));
        }

        if (nonNull(productQueryFilter.inStock())) {
            query.join(productOptionEntity).on(productOptionEntity.productOptionGroupEntity.productEntity.id.eq(productEntity.id));
        }
    }

    private BooleanBuilder satisfyProductQueryFilter(ProductQueryFilter productQueryFilter) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();

        if (nonNull(productQueryFilter.status())) {
            booleanBuilder.and(productEntity.status.eq(productQueryFilter.status()));
        } else {
            booleanBuilder.and(productEntity.status.ne(ProductStatus.DELETED));
        }

        if (nonNull(productQueryFilter.minPrice())) {
            booleanBuilder.and(productEntity.productPriceEntity.basePrice.goe(productQueryFilter.minPrice()));
        }

        if (nonNull(productQueryFilter.maxPrice())) {
            booleanBuilder.and(productEntity.productPriceEntity.basePrice.loe(productQueryFilter.maxPrice()));
        }

        if (nonNull(productQueryFilter.categoryIds()) && !productQueryFilter.categoryIds().isEmpty()) {
            booleanBuilder.and(productCategoryEntity.categoryEntity.id.in(productQueryFilter.categoryIds()));
        }

        if (nonNull(productQueryFilter.seller())) {
            booleanBuilder.and(productEntity.sellerEntity.id.eq(productQueryFilter.seller()));
        }

        if (nonNull(productQueryFilter.brand())) {
            booleanBuilder.and(productEntity.brandEntity.id.eq(productQueryFilter.brand()));
        }

        if (nonNull(productQueryFilter.inStock())) {
            booleanBuilder.and(
                    Boolean.TRUE.equals(productQueryFilter.inStock())
                            ? productOptionEntity.stock.gt(0)
                            : productOptionEntity.stock.eq(0)
            );
        }

        if (nonNull(productQueryFilter.search())) {
            booleanBuilder.and(
                    productEntity.name.containsIgnoreCase(productQueryFilter.search())
                            .or(productEntity.shortDescription.containsIgnoreCase(productQueryFilter.search()))
                            .or(productEntity.fullDescription.containsIgnoreCase(productQueryFilter.search()))
            );
        }

        return booleanBuilder;
    }
}
