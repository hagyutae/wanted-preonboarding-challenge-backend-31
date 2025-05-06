package com.sandro.wanted_shop.product.persistence;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sandro.wanted_shop.product.dto.ProductFilterDto;
import com.sandro.wanted_shop.product.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.List;

import static com.sandro.wanted_shop.brand.QBrand.brand;
import static com.sandro.wanted_shop.category.QCategory.category;
import static com.sandro.wanted_shop.product.entity.QProduct.product;
import static com.sandro.wanted_shop.tag.QTag.tag;

@RequiredArgsConstructor
@Repository
public class QueryDslProductRepositoryImpl implements QueryDslProductRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public Page<Product> findAll(Pageable pageable, ProductFilterDto filter) {
        OrderSpecifier<?>[] orderSpecifiers = getOrderSpecifiers(pageable.getSort());

        JPAQuery<Product> query = queryFactory
                .selectFrom(product)
                .leftJoin(product.brand).fetchJoin()
                .leftJoin(product.seller).fetchJoin()
                .leftJoin(product.detail).fetchJoin()
                .leftJoin(product.price).fetchJoin();

        applyFilter(query, filter);

        List<Product> content = query
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(orderSpecifiers)
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(product.count())
                .from(product);

        applyFilter(countQuery, filter);

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }

    private void applyFilter(JPAQuery<?> query, ProductFilterDto filter) {
        String keyword = filter.keyword();
        if (StringUtils.hasText(keyword)) {
            query
                    .leftJoin(product.brand)
                    .leftJoin(product.tags)
                    .leftJoin(product.categories)
                    .where(product.name.contains(keyword)
                            .or(product.fullDescription.contains(keyword))
                            .or(brand.name.contains(keyword))
                            .or(tag.name.contains(keyword))
                            .or(category.name.contains(keyword)));
        }

        Long tagId = filter.tagId();
        if (tagId != null)
            query
                    .leftJoin(product.tags)
                    .where(tag.id.eq(tagId));

        Long categoryId = filter.categoryId();
        if (categoryId != null)
            query
                    .leftJoin(product.categories)
                    .where(category.id.eq(categoryId));

        LocalDate startDate = filter.startDate();
        if (startDate != null)
            query.where(product.createdAt.after(startDate.atStartOfDay()));

        LocalDate endDate = filter.endDate();
        if (endDate != null)
            query.where(product.createdAt.before(endDate.atStartOfDay()));

        Long sellerId = filter.sellerId();
        if (sellerId != null)
            query.where(product.seller.id.eq(sellerId));

        Long brandId = filter.brandId();
        if (brandId != null)
            query.where(product.brand.id.eq(brandId));

        Integer startPrice = filter.startPrice();
        if (startPrice != null)
            query.where(product.price.basePrice.goe(startPrice));

        Integer endPrice = filter.endPrice();
        if (endPrice != null)
            query.where(product.price.basePrice.loe(endPrice));
    }

    private static OrderSpecifier<?>[] getOrderSpecifiers(Sort sort) {
        return sort.stream()
                .map(order -> new OrderSpecifier(
                        order.isAscending() ? Order.ASC : Order.DESC,
                        getTarget(order.getProperty())
                )).toArray(OrderSpecifier[]::new);
    }

    private static Expression getTarget(String property) {
        return switch (property) {
            case "createdAt" -> product.createdAt;
//            case "reviews.rating" -> product.reviews.
            case "price.basePrice" -> product.price.basePrice;
            default -> throw new IllegalStateException("Unexpected value: " + property);
        };
    }
}
