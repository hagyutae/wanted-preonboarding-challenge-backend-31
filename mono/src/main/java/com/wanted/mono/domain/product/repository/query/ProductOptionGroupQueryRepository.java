package com.wanted.mono.domain.product.repository.query;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.wanted.mono.domain.category.entity.ProductCategory;
import com.wanted.mono.domain.product.dto.model.ProductOptionGroupDto;
import com.wanted.mono.domain.product.entity.ProductOptionGroup;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.wanted.mono.domain.product.entity.QProductOption.productOption;
import static com.wanted.mono.domain.product.entity.QProductOptionGroup.productOptionGroup;


@Repository
@Slf4j
@RequiredArgsConstructor
public class ProductOptionGroupQueryRepository {
    private final JPAQueryFactory queryFactory;

    public List<ProductOptionGroup> findProductOptionGroup(Long productId) {
        return queryFactory
                .selectFrom(productOptionGroup)
                .join(productOptionGroup.productOptions, productOption).fetchJoin()
                .where(productOptionGroup.product.id.eq(productId))
                .fetch();
    }

}
