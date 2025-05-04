package sample.challengewanted.domain.product.repository.Impl;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import sample.challengewanted.domain.product.repository.ProductRepositoryCustom;
import sample.challengewanted.domain.review.QReview;
import sample.challengewanted.dto.product.*;

import java.util.List;

import static sample.challengewanted.domain.brand.QBrand.brand;
import static sample.challengewanted.domain.product.entity.QProduct.product;
import static sample.challengewanted.domain.product.entity.QProductDetail.productDetail;
import static sample.challengewanted.domain.product.entity.QProductImage.*;
import static sample.challengewanted.domain.product.entity.QProductOption.productOption;
import static sample.challengewanted.domain.product.entity.QProductOptionGroup.*;
import static sample.challengewanted.domain.product.entity.QProductPrice.productPrice;
import static sample.challengewanted.domain.seller.QSeller.seller;

@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<ProductPageResponse> searchProducts(ProductSearchCondition condition, Pageable pageable) {

        QReview review = QReview.review;

        Expression<Long> reviewCount = JPAExpressions
                .select(review.count().as("reviewCount"))
                .from(review)
                .where(review.productDetail.product.eq(product));

        List<ProductPageResponse> content = queryFactory
                .select(new QProductPageResponse(
                        product.id,
                        product.name,
                        product.slug,
                        product.shortDescription,
                        productPrice.basePrice,
                        productPrice.salePrice,
                        productPrice.currency,
                        new QImageResponse(productImage.url, productImage.altText),
                        new QBrandResponse(brand.id, brand.name),
                        new QSellerResponse(seller.id, seller.name),
                        review.rating,
                        reviewCount,
                        productOption.stock,
                        product.status,
                        product.createdAt
                ))
                .from(product)
                .leftJoin(product.brand, brand)
                .leftJoin(product.seller, seller)
                .leftJoin(product.productDetail, productDetail)
                .leftJoin(product.productDetail.reviews, review)
                .leftJoin(product.price, productPrice)
                .leftJoin(product.productImages, productImage)
                .leftJoin(product.productOptionGroups, productOptionGroup)
                .leftJoin(productOptionGroup.productOptions, productOption)
                .where(
                        eqStatus(condition.getStatus()),
                        betweenPrice(condition.getMinPrice(), condition.getMaxPrice()),
                        containsName(condition.getSearch()),
                        eqBrand(condition.getBrand()),
                        eqSeller(condition.getSeller()),
                        hasStock(condition.getInStock())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(product.createdAt.desc())
                .fetch();

        // total count 조회
        Long total = queryFactory
                .select(product.count())
                .from(product)
                .leftJoin(product.productOptionGroups, productOptionGroup)
                .leftJoin(productOptionGroup.productOptions, productOption)
                .where(
                        eqStatus(condition.getStatus()),
                        betweenPrice(condition.getMinPrice(), condition.getMaxPrice()),
                        containsName(condition.getSearch()),
                        eqBrand(condition.getBrand()),
                        eqSeller(condition.getSeller()),
                        hasStock(condition.getInStock())
                )
                .fetchOne();

        return new PageImpl<>(content, pageable, total == null ? 0 : total);
    }

    // ====== 동적 where 조건 ======
    private BooleanExpression eqStatus(String status) {
        return status != null ? product.status.eq(status) : null;
    }

    private BooleanExpression betweenPrice(Integer minPrice, Integer maxPrice) {
        if (minPrice != null && maxPrice != null) {
            return product.price.basePrice.between(minPrice, maxPrice);
        } else if (minPrice != null) {
            return product.price.basePrice.goe(minPrice);
        } else if (maxPrice != null) {
            return product.price.basePrice.loe(maxPrice);
        } else {
            return null;
        }
    }

    private BooleanExpression containsName(String search) {
        return search != null ? product.name.containsIgnoreCase(search) : null;
    }

    private BooleanExpression eqBrand(Integer brandId) {
        return brandId != null ? product.brand.id.eq(Long.valueOf(brandId)) : null;
    }

    private BooleanExpression eqSeller(Integer sellerId) {
        return sellerId != null ? product.seller.id.eq(Long.valueOf(sellerId)) : null;
    }

    private BooleanExpression hasStock(Boolean inStock) {
        if (inStock == null) return null;
        return inStock ? productOption.stock.goe(1) : productOption.stock.lt(1);
    }
}
