package com.example.demo.product.repository;

import com.example.demo.product.entity.ProductImageEntity;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

import static com.example.demo.product.entity.QProductImageEntity.productImageEntity;

@Repository
@RequiredArgsConstructor
public class ProductImageRepositoryCustomImpl implements ProductImageRepositoryCustom {
    private final JPAQueryFactory qf;

    public List<ProductImageEntity> findAllByProductIdsAndIsPrimary(Collection<Long> productIds) {
        return qf.selectFrom(productImageEntity)
                .where(
                        productImageEntity.isPrimary.eq(true),
                        productImageEntity.productEntity.id.in(productIds)
                )
                .fetch();
    }

    @Override
    public List<ProductImageEntity> findAllByProductIds(Collection<Long> productIds) {
        return qf.selectFrom(productImageEntity)
                .where(productImageEntity.productEntity.id.in(productIds))
                .fetch();
    }

    @Override
    public Long deleteByProductId(Long productId) {
        return qf.delete(productImageEntity)
                .where(productImageEntity.productEntity.id.eq(productId))
                .execute();
    }
}
