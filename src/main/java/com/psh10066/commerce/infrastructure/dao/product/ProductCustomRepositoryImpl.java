package com.psh10066.commerce.infrastructure.dao.product;

import com.psh10066.commerce.api.dto.request.GetAllProductsRequest;
import com.psh10066.commerce.api.dto.request.GetCategoryProductsRequest;
import com.psh10066.commerce.api.dto.response.GetAllProductsResponse;
import com.psh10066.commerce.domain.model.category.QCategory;
import com.psh10066.commerce.domain.model.product.Product;
import com.psh10066.commerce.domain.model.product.ProductStatus;
import com.psh10066.commerce.domain.model.review.Review;
import com.psh10066.commerce.domain.model.review.ReviewFirstCollection;
import com.psh10066.commerce.infrastructure.dao.common.OrderGenerator;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.psh10066.commerce.domain.model.brand.QBrand.brand;
import static com.psh10066.commerce.domain.model.category.QCategory.category;
import static com.psh10066.commerce.domain.model.product.QProduct.product;
import static com.psh10066.commerce.domain.model.product.QProductCategory.productCategory;
import static com.psh10066.commerce.domain.model.product.QProductImage.productImage;
import static com.psh10066.commerce.domain.model.product.QProductPrice.productPrice;
import static com.psh10066.commerce.domain.model.review.QReview.review;
import static com.psh10066.commerce.domain.model.seller.QSeller.seller;

@Repository
@RequiredArgsConstructor
public class ProductCustomRepositoryImpl implements ProductCustomRepository {

    private final JPAQueryFactory queryFactory;
    private final QCategory subCategory = new QCategory("subCategory");

    @Override
    public Page<GetAllProductsResponse> getAllProducts(GetAllProductsRequest request) {

        Pageable pageable = request.toPageable();

        List<GetAllProductsResponse> result = queryFactory.selectDistinct(Projections.constructor(GetAllProductsResponse.class,
                product.id,
                product.name,
                product.slug,
                product.shortDescription,
                productPrice.basePrice,
                productPrice.salePrice,
                productPrice.currency,
                Projections.constructor(GetAllProductsResponse.PrimaryImage.class, productImage.url, productImage.altText),
                Projections.constructor(GetAllProductsResponse.Brand.class, brand.id, brand.name),
                Projections.constructor(GetAllProductsResponse.Seller.class, seller.id, seller.name),
                Expressions.nullExpression(BigDecimal.class),
                Expressions.nullExpression(Integer.class),
                new CaseBuilder().when(product.status.eq(ProductStatus.ACTIVE)).then(true).otherwise(false),
                product.status,
                product.createdAt
            ))
            .from(product)
            .join(productPrice).on(productPrice.product.id.eq(product.id))
            .join(productImage).on(productImage.product.id.eq(product.id).and(productImage.isPrimary.isTrue()))
            .join(product.brand, brand)
            .join(product.seller, seller)
            .join(productCategory).on(productCategory.product.id.eq(product.id))
            .where(whereClauses(request))
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .orderBy(OrderGenerator.generateOrders(pageable.getSort(), Product.class))
            .fetch();

        List<Long> productIds = result.stream().map(GetAllProductsResponse::id).toList();
        List<Review> totalReviews = queryFactory
            .select(review)
            .from(review)
            .where(review.product.id.in(productIds))
            .fetch();

        // Review 추가
        Map<Long, List<Review>> reviewsByProductId = totalReviews.stream()
            .collect(Collectors.groupingBy(o -> o.getProduct().getId()));

        List<GetAllProductsResponse> newResult = new ArrayList<>();

        result.forEach(response -> {
            ReviewFirstCollection reviews = new ReviewFirstCollection(reviewsByProductId.get(response.id()));
            if (reviews.isNullOrEmpty()) {
                newResult.add(response);
                return;
            }
            newResult.add(response.toBuilder()
                .rating(reviews.getAverage())
                .reviewCount(reviews.getSize())
                .build());
        });

        return PageableExecutionUtils.getPage(newResult, pageable, () ->
            queryFactory.select(product.countDistinct())
                .from(product)
                .join(productPrice).on(productPrice.product.id.eq(product.id))
                .join(productCategory).on(productCategory.product.id.eq(product.id))
                .where(whereClauses(request))
                .fetchOne()
        );
    }

    private BooleanExpression[] whereClauses(GetAllProductsRequest request) {
        return new BooleanExpression[]{
            product.status.ne(ProductStatus.DELETED),
            request.getStatus() != null ? product.status.eq(request.getStatus()) : null,
            request.getMinPrice() != null ? productPrice.salePrice.goe(request.getMinPrice()) : null,
            request.getMaxPrice() != null ? productPrice.salePrice.loe(request.getMaxPrice()) : null,
            request.getCategory() != null ? productCategory.category.id.in(request.getCategory()) : null,
            request.getSeller() != null ? product.seller.id.eq(request.getSeller()) : null,
            request.getBrand() != null ? product.brand.id.eq(request.getBrand()) : null,
            request.getInStock() != null ? product.status.eq(ProductStatus.ACTIVE) : null,
            StringUtils.hasText(request.getSearch()) ? product.name.contains(request.getSearch()) : null
        };
    }

    @Override
    public Page<GetAllProductsResponse> getCategoryProducts(Long categoryId, GetCategoryProductsRequest request) {

        Pageable pageable = request.toPageable();

        List<GetAllProductsResponse> result = queryFactory.selectDistinct(Projections.constructor(GetAllProductsResponse.class,
                product.id,
                product.name,
                product.slug,
                product.shortDescription,
                productPrice.basePrice,
                productPrice.salePrice,
                productPrice.currency,
                Projections.constructor(GetAllProductsResponse.PrimaryImage.class, productImage.url, productImage.altText),
                Projections.constructor(GetAllProductsResponse.Brand.class, brand.id, brand.name),
                Projections.constructor(GetAllProductsResponse.Seller.class, seller.id, seller.name),
                Expressions.nullExpression(BigDecimal.class),
                Expressions.nullExpression(Integer.class),
                new CaseBuilder().when(product.status.eq(ProductStatus.ACTIVE)).then(true).otherwise(false),
                product.status,
                product.createdAt
            ))
            .from(product)
            .join(productPrice).on(productPrice.product.id.eq(product.id))
            .join(productImage).on(productImage.product.id.eq(product.id).and(productImage.isPrimary.isTrue()))
            .join(product.brand, brand)
            .join(product.seller, seller)
            .join(productCategory).on(productCategory.product.id.eq(product.id))
            .join(productCategory.category, category)
            .join(category.children, subCategory)
            .where(whereClauses(categoryId, request))
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .orderBy(OrderGenerator.generateOrders(pageable.getSort(), Product.class))
            .fetch();

        List<Long> productIds = result.stream().map(GetAllProductsResponse::id).toList();
        List<Review> totalReviews = queryFactory
            .select(review)
            .from(review)
            .where(review.product.id.in(productIds))
            .fetch();

        // Review 추가
        Map<Long, List<Review>> reviewsByProductId = totalReviews.stream()
            .collect(Collectors.groupingBy(o -> o.getProduct().getId()));

        List<GetAllProductsResponse> newResult = new ArrayList<>();

        result.forEach(response -> {
            ReviewFirstCollection reviews = new ReviewFirstCollection(reviewsByProductId.get(response.id()));
            if (reviews.isNullOrEmpty()) {
                newResult.add(response);
                return;
            }
            newResult.add(response.toBuilder()
                .rating(reviews.getAverage())
                .reviewCount(reviews.getSize())
                .build());
        });

        return PageableExecutionUtils.getPage(newResult, pageable, () ->
            queryFactory.select(product.countDistinct())
                .from(product)
                .join(productPrice).on(productPrice.product.id.eq(product.id))
                .join(productCategory).on(productCategory.product.id.eq(product.id))
                .where(whereClauses(categoryId, request))
                .fetchOne()
        );
    }

    private BooleanExpression[] whereClauses(Long categoryId, GetCategoryProductsRequest request) {
        return new BooleanExpression[]{
            product.status.ne(ProductStatus.DELETED),
            request.getIncludeSubcategories() ? category.id.eq(categoryId).or(subCategory.id.eq(categoryId)) : category.id.eq(categoryId),
        };
    }
}
