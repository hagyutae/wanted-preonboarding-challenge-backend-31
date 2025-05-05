package com.preonboarding.repository.product.querydsl;

import com.preonboarding.domain.Product;
import com.preonboarding.domain.QProduct;
import com.preonboarding.domain.QProductCategory;
import com.preonboarding.dto.request.product.ProductSearchRequestDto;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ProductSearchRepositoryImpl implements ProductSearchRepository {
    private final JPAQueryFactory queryFactory;
    private final QProduct product = QProduct.product;
    private final QProductCategory productCategory = QProductCategory.productCategory;

    @Override
    public Page<Product> findProductsBySearch(Pageable pageable,ProductSearchRequestDto dto) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();

        if (dto.getBrand() != null) {
            booleanBuilder.and(product.brand.id.eq(dto.getBrand()));
        }

        if (dto.getSeller() != null) {
            booleanBuilder.and(product.seller.id.eq(dto.getSeller()));
        }

        if (dto.getMinPrice() != null && dto.getMaxPrice()!=null) {
            booleanBuilder.and(product.productPrice.basePrice.between(dto.getMinPrice(),dto.getMaxPrice()));
        }

        if (dto.getStatus() != null && !dto.getStatus().isEmpty()) {
            booleanBuilder.and(product.status.eq(dto.getStatus()));
        }

        if (dto.getCategory()!=null && !dto.getCategory().isEmpty()) {
            booleanBuilder.and(product.id.in(
                    queryFactory.select(productCategory.product.id)
                            .from(productCategory)
                            .where(productCategory.category.id.in(dto.getCategory()))
            ));
        }

        if (dto.getSearch() != null && !dto.getSearch().isEmpty()) {
            booleanBuilder.and(product.name.containsIgnoreCase(dto.getSearch()));
        }

        List<Product> productList = queryFactory.selectFrom(product)
                .where(booleanBuilder)
                .offset(pageable.getPageNumber())
                .limit(pageable.getPageSize())
                .fetch();

        Long total = queryFactory.select(product.count())
                .from(product)
                .where(booleanBuilder)
                .fetchOne();

        return new PageImpl<>(productList, pageable, total==null ? 0:total);
    }
}
