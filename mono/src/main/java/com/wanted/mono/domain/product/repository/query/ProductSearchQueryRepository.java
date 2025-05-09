package com.wanted.mono.domain.product.repository.query;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.wanted.mono.domain.product.dto.request.ProductSearchRequest;
import com.wanted.mono.domain.product.entity.Product;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;


import static com.wanted.mono.domain.brand.entity.QBrand.brand;
import static com.wanted.mono.domain.category.entity.QCategory.category;
import static com.wanted.mono.domain.category.entity.QProductCategory.productCategory;
import static com.wanted.mono.domain.product.entity.QProduct.product;
import static com.wanted.mono.domain.product.entity.QProductPrice.productPrice;
import static com.wanted.mono.domain.seller.entity.QSeller.seller;


@Repository
@RequiredArgsConstructor
@Slf4j
public class ProductSearchQueryRepository {

    private final JPAQueryFactory queryFactory;

    /**
     * 필터링 조건 + 페이징 검색 쿼리
     * @param req
     * @return
     */
    public Page<Product> search(ProductSearchRequest req) {
        log.info("ProductQueryRepository where 절 생성");
        BooleanExpression where = buildWhere(req);

        log.info("ProductQueryRepository 오프셋 계산");
        int offset = (req.getPage() - 1) * req.getPerPage();

        log.info("ProductQueryRepository 정렬 기준 계산");
        OrderSpecifier<?> orderSpecifier = buildOrderSpecifier(req.getSort());

        List<Product> content = queryFactory
                .select(product)
                .from(product)
                .join(product.productPrices, productPrice)
                .join(product.productCategories, productCategory)
                .join(productCategory.category, category)
                .join(product.seller, seller).fetchJoin()
                .join(product.brand, brand).fetchJoin()
                .where(where)
                .orderBy(orderSpecifier)
                .offset(offset)
                .limit(req.getPerPage())
                .fetch();

        long total = queryFactory
                .select(product.count())
                .from(product)
                .innerJoin(product.productPrices, productPrice)
                .innerJoin(product.productCategories, productCategory)
                .innerJoin(product.seller, seller)
                .innerJoin(product.brand, brand)
                .where(where)
                .fetchOne();

        return new PageImpl<>(content, PageRequest.of(req.getPage() - 1, req.getPerPage()), total);
    }

    private BooleanExpression buildWhere(ProductSearchRequest req) {
        List<BooleanExpression> predicates = new ArrayList<>();

        // 상품 상태 필터 여부 확인
        if (req.getStatus() != null) {
            predicates.add(product.status.eq(req.getStatus()));
        }

        // 최소 가격 필터 여부 확인
        if (req.getMinPrice() != null) {
            predicates.add(productPrice.basePrice.goe(req.getMinPrice()));
        }

        // 최대 가격 필터 여부 확인
        if (req.getMaxPrice() != null) {
            predicates.add(productPrice.basePrice.loe(req.getMaxPrice()));
        }

        // 카테고리 필터 여부 확인
        if (req.getCategory() != null && !req.getCategory().isEmpty()) {
            predicates.add(category.id.in(req.getCategory()));
        }

        // 판매자 ID 필터 여부 확인
        if (req.getSeller() != null) {
            predicates.add(seller.id.eq(req.getSeller()));
        }

        // 브랜드 ID 필터 여부 확인
        if (req.getBrand() != null) {
            predicates.add(brand.id.eq(req.getBrand()));
        }

        // 검색어 필터 여부 확인
        if (req.getSearch() != null) {
            String keyword = req.getSearch();
            predicates.add(
                    product.name.contains(keyword)
                            .or(seller.name.contains(keyword)
                                    .or(brand.name.contains(keyword))));
        }

        return predicates.stream()
                .reduce(BooleanExpression::and)
                .orElse(null);
    }

    public OrderSpecifier<?> buildOrderSpecifier(String sortParam) {
        if (sortParam == null || sortParam.isBlank()) {
            return product.createdAt.desc(); // 기본 정렬 조건
        }

        String[] parts = sortParam.split(":");
        String field = parts[0];
        boolean isDesc = parts.length > 1 && parts[1].equalsIgnoreCase("desc");

        switch (field) {
            case "created_at":
                return isDesc ? product.createdAt.desc() : product.createdAt.asc();
            case "name":
                return isDesc ? product.name.desc() : product.name.asc();
            case "price":
                return isDesc ? productPrice.basePrice.desc() : productPrice.basePrice.asc();
            default:
                throw new IllegalArgumentException("지원하지 않는 정렬 필드입니다: " + field);
        }
    }


}

