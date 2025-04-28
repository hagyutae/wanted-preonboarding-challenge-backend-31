package com.ecommerce.product.infrastructure.impl;

import com.ecommerce.brand.domain.QBrand;
import com.ecommerce.product.application.dto.req.ProductSearchRequest;
import com.ecommerce.product.domain.Product;
import com.ecommerce.product.domain.QCategory;
import com.ecommerce.product.domain.QProduct;
import com.ecommerce.product.domain.QProductCategory;
import com.ecommerce.product.domain.QProductImage;
import com.ecommerce.product.domain.QProductOption;
import com.ecommerce.product.domain.QProductPrice;
import com.ecommerce.product.infrastructure.ProductRepositoryCustom;
import com.ecommerce.seller.domain.QSeller;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.math.BigDecimal;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepositoryCustom {
    private final JPAQueryFactory queryFactory;
    private final QProduct product = QProduct.product;
    private final QProductPrice price = QProductPrice.productPrice;
    private final QProductCategory category = QProductCategory.productCategory;
    private final QCategory categoryEntity = QCategory.category;
    private final QSeller seller = QSeller.seller;
    private final QBrand brand = QBrand.brand;
    private final QProductOption option = QProductOption.productOption;
    private final QProductImage image = QProductImage.productImage;

    @Override
    public Page<Product> findBySearchRequest(ProductSearchRequest request, Pageable pageable) {
        var query = queryFactory.select(product)
                .from(product)
                .join(product.brand, brand).fetchJoin()
                .join(product.seller, seller).fetchJoin();

        // 상태 필터링
        if (request.getStatus() != null && !request.getStatus().isEmpty()) {
            query.where(product.status.stringValue().eq(request.getStatus()));
        }

        // 브랜드 필터링
        if (request.getBrand() != null) {
            query.where(brand.id.eq(request.getBrand().longValue()));
        }

        // 판매자 필터링
        if (request.getSeller() != null) {
            query.where(seller.id.eq(request.getSeller().longValue()));
        }

        // 가격 필터링 - 가격 테이블과 JOIN
        if (request.getMinPrice() != null || request.getMaxPrice() != null) {
            query.join(price).on(price.product.eq(product));

            if (request.getMinPrice() != null) {
                query.where(price.basePrice.goe(new BigDecimal(request.getMinPrice())));
            }

            if (request.getMaxPrice() != null) {
                query.where(price.basePrice.loe(new BigDecimal(request.getMaxPrice())));
            }
        }

        // 카테고리 필터링
        if (request.getCategory() != null && !request.getCategory().isEmpty()) {
            query.join(category).on(category.product.eq(product))
                    .join(category.category, categoryEntity);

            var categoryIds = request.getCategory().stream()
                    .map(Integer::longValue)
                    .toList();

            query.where(categoryEntity.id.in(categoryIds));
        }

        // 재고 여부 필터링
        if (request.getInStock() != null) {
            query.join(option).on(option.optionGroup.product.eq(product));

            if (Boolean.TRUE.equals(request.getInStock())) {
                query.where(option.stock.gt(0));
            } else {
                query.where(option.stock.eq(0));
            }
        }

        // 검색어 필터링
        if (request.getSearch() != null && !request.getSearch().isEmpty()) {
            String searchTerm = "%" + request.getSearch() + "%";
            query.where(
                    product.name.like(searchTerm)
                            .or(product.shortDescription.like(searchTerm))
                            .or(product.fullDescription.like(searchTerm))
            );
        }

        // Sort 파라미터 처리
        String sortParam = request.getSort();
        if (sortParam != null) {
            String[] sortParts = sortParam.split(":");
            String field = sortParts[0];
            boolean isAsc = sortParts.length < 2 || "asc".equalsIgnoreCase(sortParts[1]);

            switch (field) {
                case "created_at":
                    query.orderBy(isAsc ? product.createdAt.asc() : product.createdAt.desc());
                    break;
                case "name":
                    query.orderBy(isAsc ? product.name.asc() : product.name.desc());
                    break;
                case "price":
                    query.join(price).on(price.product.eq(product));
                    query.orderBy(isAsc ? price.basePrice.asc() : price.basePrice.desc());
                    break;
                default:
                    query.orderBy(product.createdAt.desc());
                    break;
            }
        } else {
            query.orderBy(product.createdAt.desc());
        }

        // Count 쿼리와 결과 쿼리를 분리하여 실행
        var countQuery = queryFactory.select(product.count())
                .from(product);

        // 필터링 조건 동일하게 적용
        if (request.getStatus() != null && !request.getStatus().isEmpty()) {
            countQuery.where(product.status.stringValue().eq(request.getStatus()));
        }

        if (request.getBrand() != null) {
            countQuery.where(brand.id.eq(request.getBrand().longValue()));
        }

        if (request.getSeller() != null) {
            countQuery.where(seller.id.eq(request.getSeller().longValue()));
        }

        // 페이징 적용
        query.offset(pageable.getOffset())
                .limit(pageable.getPageSize());

        long total = countQuery.fetchOne();
        List<Product> products = query.fetch();

        return new PageImpl<>(products, pageable, total);
    }

}
