package com.wanted.mono.domain.category.repository.query;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.wanted.mono.domain.category.entity.ProductCategory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.wanted.mono.domain.category.entity.QCategory.category;
import static com.wanted.mono.domain.category.entity.QProductCategory.productCategory;

@Repository
@Slf4j
@RequiredArgsConstructor
public class CategoryQueryRepository {
    private final JPAQueryFactory queryFactory;

    public List<ProductCategory> findProductCategories(Long productId) {
        return queryFactory
                .selectFrom(productCategory)
                .join(productCategory.category, category).fetchJoin()
                .where(productCategory.product.id.eq(productId))
                .fetch();
    }
}
