package com.wanted.mono.domain.product.repository.query;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.wanted.mono.domain.product.entity.ProductImage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.wanted.mono.domain.product.entity.QProductImage.productImage;
import static com.wanted.mono.domain.product.entity.QProductOption.productOption;

@Repository
@Slf4j
@RequiredArgsConstructor
public class ProductImageQueryRepository {
    private final JPAQueryFactory queryFactory;

    public List<ProductImage> findByProductIdWithProductOption(final Long productId) {
        return queryFactory.select(productImage)
                .from(productImage)
                .join(productImage.option, productOption).fetchJoin()
                .where(productImage.product.id.eq(productId))
                .fetch();
    }
}
