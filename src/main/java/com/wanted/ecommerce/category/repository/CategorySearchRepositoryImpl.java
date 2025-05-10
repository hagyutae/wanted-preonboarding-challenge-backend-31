package com.wanted.ecommerce.category.repository;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.wanted.ecommerce.brand.domain.QBrand;
import com.wanted.ecommerce.category.domain.QCategory;
import com.wanted.ecommerce.common.dto.response.ProductItemResponse;
import com.wanted.ecommerce.product.domain.QProduct;
import com.wanted.ecommerce.product.domain.QProductImage;
import com.wanted.ecommerce.product.domain.QProductOption;
import com.wanted.ecommerce.product.domain.QProductOptionGroup;
import com.wanted.ecommerce.product.domain.QProductPrice;
import com.wanted.ecommerce.review.domain.QReview;
import com.wanted.ecommerce.seller.domain.QSeller;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CategorySearchRepositoryImpl implements CategorySearchRepository {

    private final JPAQueryFactory queryFactory;
    private static final QProduct product = QProduct.product;
    private final QProductPrice price = QProductPrice.productPrice;
    private final QProductImage image = QProductImage.productImage;
    private static final QProductOption option = QProductOption.productOption;
    private static final QProductOptionGroup optionGroup = QProductOptionGroup.productOptionGroup;
    private static final QReview review = QReview.review;
    private final static QCategory category = QCategory.category;
    private final QSeller seller = QSeller.seller;
    private final QBrand brand = QBrand.brand;

    @Override
    public Page<ProductItemResponse> findAllByCategoryIds(List<Long> categoryIds,
        Pageable pageable) {
        var query = queryFactory
            .select(Projections.constructor(ProductItemResponse.class,
                product.id,
                product.name,
                product.slug,
                product.shortDescription,
                price.basePrice,
                price.salePrice,
                price.currency,
                image.url,
                image.altText,
                brand.id,
                brand.name,
                seller.id,
                seller.name,
                getRatingAvg(),
                getReviewCount(),
                getInStock(),
                product.status,
                product.createdAt
            ))
            .from(product)
            .innerJoin(product.price, price)
            .innerJoin(product.brand, brand)
            .innerJoin(product.seller, seller)
            .innerJoin(product.images, image);

        List<Predicate> conditions = new ArrayList<>();

        if (categoryIds != null && !categoryIds.isEmpty()) {
            conditions.add(category.id.in(categoryIds.stream()
                .toList()));
        }
        conditions.add(image.primary.eq(true));
        query.where(conditions.toArray(new Predicate[0]));

        List<OrderSpecifier<?>> orders = getSortOrder(query,pageable);
        if(!orders.isEmpty()){
            query.orderBy(orders.toArray(OrderSpecifier[]::new));
        }
        else {
            query.orderBy(product.createdAt.desc());
        }

        query.offset(pageable.getOffset())
            .limit(pageable.getPageSize());

        List<ProductItemResponse> result = query.fetch();
        return PageableExecutionUtils.getPage(result, pageable, () -> query.fetch().size());
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

    private static JPQLQuery<Boolean> getInStock() {
        return JPAExpressions.select(
                option.stock.count().goe(0)
            )
            .from(option)
            .leftJoin(option.optionGroup, optionGroup)
            .leftJoin(optionGroup.product, product)
            .where(optionGroup.product.eq(product));
    }

    private static List<OrderSpecifier<?>> getSortOrder(
        JPAQuery<ProductItemResponse> query, Pageable pageable) {
        List<OrderSpecifier<?>> orders = new ArrayList<>();
        if (!pageable.getSort().isEmpty()) {
            for (Sort.Order order : pageable.getSort()) {
                Order sortOrder = order.getDirection().isAscending() ? Order.ASC : Order.DESC;

                switch (order.getProperty()) {
                    case "created_at" ->
                        orders.add(new OrderSpecifier<>(sortOrder, product.createdAt));
                    case "price" -> orders.add(new OrderSpecifier<>(sortOrder, product.price.basePrice));
                    case "rating" -> {
                        QReview review = QReview.review;
                        query.leftJoin(review).on(review.product.eq(product));
                        query.groupBy(product.id);
                        orders.add(new OrderSpecifier<>(sortOrder, review.rating.avg()));
                    }
                }
            }
        }
        return orders;
    }
}
