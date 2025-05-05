package com.example.cqrsapp.product.repository.querydsl;

import com.example.cqrsapp.common.dto.ProductSummaryItem;
import com.example.cqrsapp.product.repository.CategoryRepositoryCustom;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.ArrayList;
import java.util.List;

import static com.example.cqrsapp.product.domain.QBrand.brand;
import static com.example.cqrsapp.product.domain.QProduct.product;
import static com.example.cqrsapp.product.domain.QProductCategory.productCategory;
import static com.example.cqrsapp.product.domain.QProductImage.productImage;
import static com.example.cqrsapp.product.domain.QProductOption.productOption;
import static com.example.cqrsapp.product.domain.QProductOptionGroup.productOptionGroup;
import static com.example.cqrsapp.product.domain.QProductPrice.productPrice;
import static com.example.cqrsapp.review.domain.QReview.review;
import static com.example.cqrsapp.seller.domain.QSeller.seller;

public class CategoryRepositoryCustomImpl implements CategoryRepositoryCustom {

    private final EntityManager em;
    private JPAQueryFactory queryFactory;

    public CategoryRepositoryCustomImpl(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<ProductSummaryItem> findAllByCategoryIdIn(List<Long> categoryIds, Pageable pageable) {
        List<ProductSummaryItem> result = queryFactory
                .select(Projections.constructor(ProductSummaryItem.class,
                        product.id,
                        product.name,
                        product.slug,
                        product.shortDescription,
                        productPrice.basePrice,
                        productPrice.salePrice,
                        productPrice.currency,
                        productImage.url,
                        productImage.altText,
                        brand.id,
                        brand.name,
                        seller.id,
                        seller.name,
                        getRatingAvg(),
                        getReviewCount(),
                        stockCount(),
                        product.status,
                        product.createdAt
                ))
                .from(product)
                .innerJoin(product.productPrice, productPrice)
                .innerJoin(product.brand, brand)
                .innerJoin(product.seller, seller)
                .innerJoin(product.productImages, productImage)
                .where(isPrimaryImage(), categoryIn(categoryIds))
                .orderBy(getAllOrderSpecifiers(pageable).toArray(OrderSpecifier[]::new))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();


        return PageableExecutionUtils.getPage(result, pageable, () -> productTotalCount(categoryIds));
    }

    private Long productTotalCount(List<Long> categoryIds) {
        return queryFactory
                .select(product.count())
                .from(product)
                .innerJoin(product.productPrice, productPrice)
                .innerJoin(product.brand, brand)
                .innerJoin(product.seller, seller)
                .innerJoin(product.productImages, productImage)
                .where(isPrimaryImage(), categoryIn(categoryIds))
                .fetchOne();
    }

    private static BooleanExpression categoryIn(List<Long> category) {
        return category != null ? JPAExpressions.selectFrom(productCategory)
                .where(productCategory.id.in(category),
                        productCategory.product.eq(product)).exists()
                : null;
    }


    private static BooleanExpression isPrimaryImage() {
        return productImage.isPrimary.eq(true);
    }

    private static JPQLQuery<Long> stockCount() {
        return JPAExpressions.select(productOption.stock.count())
                .from(productOption)
                .leftJoin(productOption.optionGroup, productOptionGroup)
                .leftJoin(productOptionGroup.product, product)
                .where(productOptionGroup.product.eq(product));
    }

    private static JPQLQuery<Double> getRatingAvg() {
        return JPAExpressions.select(review.rating.avg())
                .from(review)
                .where(review.product.eq(product));
    }

    private static JPQLQuery<Long> getReviewCount() {
        return JPAExpressions.select(review.count())
                .from(review)
                .where(review.product.eq(product));
    }


    private List<OrderSpecifier<?>> getAllOrderSpecifiers(Pageable pageable) {
        List<OrderSpecifier<?>> orders = new ArrayList<>();
        if (!pageable.getSort().isEmpty()) {
            for (Sort.Order order : pageable.getSort()) {
                Order direction = order.getDirection().isAscending() ? Order.ASC : Order.DESC;

                switch (order.getProperty()) {
                    case "id":
                        orders.add(new OrderSpecifier<>(direction, product.id));
                        break;
                    case "name":
                        orders.add(new OrderSpecifier<>(direction, product.name));
                        break;
                    default:
                        orders.add(new OrderSpecifier<>(direction, product.createdAt));
                        break;
                }
            }
        }

        return orders;
    }
}
