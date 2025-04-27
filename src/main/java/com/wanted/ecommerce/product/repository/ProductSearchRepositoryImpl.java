package com.wanted.ecommerce.product.repository;

import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.wanted.ecommerce.brand.domain.QBrand;
import com.wanted.ecommerce.category.domain.QCategory;
import com.wanted.ecommerce.product.domain.Product;
import com.wanted.ecommerce.product.domain.ProductStatus;
import com.wanted.ecommerce.product.domain.QProduct;
import com.wanted.ecommerce.product.domain.QProductCategory;
import com.wanted.ecommerce.product.domain.QProductOption;
import com.wanted.ecommerce.product.domain.QProductPrice;
import com.wanted.ecommerce.product.dto.request.ProductReadAllRequest;
import com.wanted.ecommerce.review.domain.QReview;
import com.wanted.ecommerce.seller.domain.QSeller;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ProductSearchRepositoryImpl implements ProductSearchRepository{
    private final JPAQueryFactory queryFactory;
    private final QProduct product = QProduct.product;
    private final QProductPrice price = QProductPrice.productPrice;
    private final QProductCategory productCategory = QProductCategory.productCategory;
    private final QCategory category = QCategory.category;
    private final QSeller seller = QSeller.seller;
    private final QBrand brand = QBrand.brand;
    private final QProductOption option = QProductOption.productOption;

    @Override
    public PageImpl<Product> findAllByRequest(ProductReadAllRequest request, Pageable pageable) {
        var query = queryFactory
            .selectFrom(product)
            .join(product.brand, brand).fetchJoin()
            .join(product.seller, seller).fetchJoin();

        // dynamic where
        List<Predicate> conditions = new ArrayList<>();
        // status
        if (request.getStatus() != null && !request.getStatus().isBlank()) {
            conditions.add(product.status.eq(ProductStatus.valueOf(request.getStatus())));
        }

        // price (min, max)
        if (request.getMinPrice() != null || request.getMaxPrice() != null) {
            query.leftJoin(product.prices, price);

            if (request.getMinPrice() != null) {
                conditions.add(price.basePrice.goe(BigDecimal.valueOf(request.getMinPrice())));
            }

            if (request.getMaxPrice() != null) {
                conditions.add(price.basePrice.loe(BigDecimal.valueOf(request.getMaxPrice())));
            }
        }

        // category
        if (request.getCategory() != null && !request.getCategory().isEmpty()) {
            query.leftJoin(product.categories, productCategory)
                .leftJoin(productCategory.category, category);
            conditions.add(category.id.in(request.getCategory().stream()
                .map(Long::valueOf)
                .toList()));
        }

        // seller
        if (request.getSeller() != null) {
            conditions.add(seller.id.eq(request.getSeller().longValue()));
        }
        // brand
        if (request.getBrand() != null) {
            conditions.add(brand.id.eq(request.getBrand().longValue()));
        }
        // stock
        if (request.getInStock() != null) {
            query.join(option).on(option.optionGroup.product.eq(product));

            if (Boolean.TRUE.equals(request.getInStock())) {
                conditions.add(option.stock.gt(0));
            } else {
                conditions.add(option.stock.eq(0));
            }
        }
        // search
        if (request.getSearch() != null && !request.getSearch().isBlank()) {
            String keyword = "%" + request.getSearch() + "%";
            conditions.add(
                product.name.like(keyword)
                    .or(product.shortDescription.like(keyword))
                    .or(product.fullDescription.like(keyword))
            );
        }
        query.where(conditions.toArray(new Predicate[0]));

        // sort
        if (request.getSort() != null) {
            String[] sortParts = request.getSort().split(":");
            String field = sortParts[0];
            boolean isAsc = sortParts.length < 2 || "asc".equalsIgnoreCase(sortParts[1]);

            switch (field) {
                case "created_at" -> query.orderBy(isAsc ? product.createdAt.asc() : product.createdAt.desc());
                case "price" -> {
                    if(request.getMaxPrice() == null & request.getMaxPrice() == null) query.leftJoin(product.prices, price);
                    query.orderBy(isAsc ? price.basePrice.asc() : price.basePrice.desc());
                }
                case "rating" -> {
                    QReview review = QReview.review;
                    query.leftJoin(review).on(review.product.eq(product));
                    query.groupBy(product.id);
                    query.orderBy(isAsc ? review.rating.avg().asc() : review.rating.avg().desc());
                }
                default -> query.orderBy(product.createdAt.desc());
            }
        } else {
            query.orderBy(product.createdAt.desc());
        }

        // paging
        query.offset(pageable.getOffset())
            .limit(pageable.getPageSize());

        // execute
        List<Product> products = query.fetch();

        // count query
        long total = query.fetch().size();
        return new PageImpl<>(products, pageable, total);
    }

}
