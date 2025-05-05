package com.dino.cqrs_challenge.domain.repository;

import com.dino.cqrs_challenge.domain.entity.Product;
import com.dino.cqrs_challenge.domain.entity.QCategory;
import com.dino.cqrs_challenge.domain.entity.QProduct;
import com.dino.cqrs_challenge.domain.entity.QProductCategory;
import com.dino.cqrs_challenge.domain.entity.QProductOption;
import com.dino.cqrs_challenge.domain.entity.QProductOptionGroup;
import com.dino.cqrs_challenge.domain.entity.QProductPrice;
import com.dino.cqrs_challenge.domain.enums.ProductStatus;
import com.dino.cqrs_challenge.presentation.model.rq.ProductSearchCondition;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<Product> searchProducts(ProductSearchCondition cond) {
        QProduct product = QProduct.product;
        QProductPrice productPrice = QProductPrice.productPrice;
        QProductCategory productCategory = QProductCategory.productCategory;
        QCategory category = QCategory.category;

        // paging 처리
        int page = cond.getPage() != null && cond.getPage() > 0 ? cond.getPage() - 1 : 0;
        int perPage = cond.getPerPage() != null && cond.getPerPage() > 0 ? cond.getPerPage() : 10;

        JPAQuery<Product> query = queryFactory
                .selectDistinct(product)
                .from(product)
                .join(productPrice).on(productPrice.product.id.eq(product.id))
                .leftJoin(productCategory).on(productCategory.product.id.eq(product.id))
                .leftJoin(category).on(category.id.eq(productCategory.category.id))
                .where(
                        statusEq(cond.getStatus()),
                        minPriceGoe(cond.getMinPrice()),
                        maxPriceLoe(cond.getMaxPrice()),
                        categoryIn(cond.getCategory()),
                        sellerEq(cond.getSeller()),
                        brandEq(cond.getBrand()),
                        inStock(cond.getInStock()),
                        keywordContains(cond.getSearch()));
        // 메인 쿼리
        List<OrderSpecifier<?>> orderSpecifiers = buildSortOrder(cond.getSort(), product);
        for (OrderSpecifier<?> order : orderSpecifiers) {
            query.orderBy(order);
        }

        List<Product> results = query.offset((long) page * perPage)
                .limit(perPage)
                .fetch();

        Long total = queryFactory
                .select(product.countDistinct())
                .from(product)
                .join(productPrice).on(productPrice.product.id.eq(product.id))
                .leftJoin(productCategory).on(productCategory.product.id.eq(product.id))
                .leftJoin(category).on(category.id.eq(productCategory.category.id))
                .where(
                        statusEq(cond.getStatus()),
                        minPriceGoe(cond.getMinPrice()),
                        maxPriceLoe(cond.getMaxPrice()),
                        categoryIn(cond.getCategory()),
                        sellerEq(cond.getSeller()),
                        brandEq(cond.getBrand()),
                        inStock(cond.getInStock()),
                        keywordContains(cond.getSearch()))
                .fetchOne();

        return new PageImpl<>(results, PageRequest.of(page, perPage), Optional.ofNullable(total).orElse(0L));
    }

    private Predicate keywordContains(String search) {
        if (!StringUtils.hasText(search)) {
            return null;
        }
        QProduct product = QProduct.product;
        return product.name.containsIgnoreCase(search).or(product.shortDescription.containsIgnoreCase(search)).or(product.fullDescription.containsIgnoreCase(search));
    }

    private Predicate inStock(Boolean inStock) {
        if (inStock == null) {
            return null;
        }

        QProduct product = QProduct.product;
        QProductOptionGroup optionGroup = QProductOptionGroup.productOptionGroup;
        QProductOption option = QProductOption.productOption;
        if (inStock) {
            return JPAExpressions.selectOne().from(option).join(option.productOptionGroup, optionGroup).where(optionGroup.product.eq(product), option.stock.gt(0)).exists();
        } else {
            return JPAExpressions.selectOne().from(option).join(option.productOptionGroup, optionGroup).where(optionGroup.product.eq(product), option.stock.eq(0)).exists();
        }
    }

    private Predicate brandEq(Long brand) {
        if (brand == null) {
            return null;
        }
        return QProduct.product.brand.id.eq(brand);
    }

    private Predicate sellerEq(Long seller) {
        if (seller == null) {
            return null;
        }
        return QProduct.product.seller.id.eq(seller);
    }

    private Predicate categoryIn(List<Long> category) {
        if (CollectionUtils.isEmpty(category)) {
            return null;
        }
        return QProduct.product.productCategories.any().category.id.in(category);
    }

    private Predicate maxPriceLoe(Integer maxPrice) {
        if (maxPrice == null) {
            return null;
        }
        return QProductPrice.productPrice.salePrice.loe(BigDecimal.valueOf(maxPrice));
    }

    private Predicate minPriceGoe(Integer minPrice) {
        if (minPrice == null) {
            return null;
        }
        return QProductPrice.productPrice.salePrice.goe(BigDecimal.valueOf(minPrice));
    }

    private Predicate statusEq(ProductStatus status) {
        if (status == null) {
            return null;
        }
        return QProduct.product.status.eq(status);
    }

    public List<OrderSpecifier<?>> buildSortOrder(String sortParam, QProduct product) {
        if (sortParam == null || sortParam.isBlank()) {
            return List.of(product.createdAt.desc()); // 기본 정렬
        }

        String[] sortFields = sortParam.split(",");
        List<OrderSpecifier<?>> orderSpecifiers = new ArrayList<>();

        for (String sortField : sortFields) {
            String[] parts = sortField.split(":");
            if (parts.length != 2) continue;

            String field = parts[0].trim();
            String direction = parts[1].trim().toLowerCase();

            Order order = direction.equals("asc") ? Order.ASC : Order.DESC;

            switch (field) {
                case "created_at" -> orderSpecifiers.add(new OrderSpecifier<>(order, product.createdAt));
                case "name" -> orderSpecifiers.add(new OrderSpecifier<>(order, product.name));
                case "status" -> orderSpecifiers.add(new OrderSpecifier<>(order, product.status));
                // 필요한 필드는 여기에 추가
            }
        }

        return orderSpecifiers.isEmpty() ? List.of(product.createdAt.desc()) : orderSpecifiers;
    }
}
