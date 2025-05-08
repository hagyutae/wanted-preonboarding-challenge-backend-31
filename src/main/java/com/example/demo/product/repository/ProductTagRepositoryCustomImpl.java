package com.example.demo.product.repository;

import com.example.demo.product.entity.ProductTagEntity;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.demo.product.entity.QProductTagEntity.productTagEntity;
import static com.example.demo.tag.entity.QTagEntity.tagEntity;

@Repository
@RequiredArgsConstructor
public class ProductTagRepositoryCustomImpl implements ProductTagRepositoryCustom {
    private final JPAQueryFactory qf;

    public List<ProductTagEntity> findAllByProductId(Long productId) {
        return qf.select(productTagEntity)
                .from(productTagEntity)
                .leftJoin(productTagEntity.tagEntity, tagEntity).fetchJoin()
                .where(productTagEntity.productEntity.id.eq(productId))
                .fetch();
    }

    @Override
    public Long deleteByProductId(Long productId) {
        return qf.delete(productTagEntity)
                .where(productTagEntity.productEntity.id.eq(productId))
                .execute();
    }
}
