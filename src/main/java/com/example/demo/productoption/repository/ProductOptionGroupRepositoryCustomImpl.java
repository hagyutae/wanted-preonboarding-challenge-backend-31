package com.example.demo.productoption.repository;


import com.example.demo.productoption.entity.ProductOptionGroupEntity;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.example.demo.productoption.entity.QProductOptionEntity.productOptionEntity;
import static com.example.demo.productoption.entity.QProductOptionGroupEntity.productOptionGroupEntity;

@Repository
@RequiredArgsConstructor
public class ProductOptionGroupRepositoryCustomImpl implements ProductOptionGroupRepositoryCustom {
    private final JPAQueryFactory qf;

    @Override
    public List<ProductOptionGroupEntity> findAllByProductId(Long productId) {
        return qf.select(productOptionGroupEntity)
                .from(productOptionGroupEntity)
                .join(productOptionGroupEntity.productOptionEntityList, productOptionEntity).fetchJoin()
                .where(productOptionGroupEntity.productEntity.id.eq(productId))
                .fetch();
    }

    @Override
    public Long deleteByProductId(Long productId) {
        return qf.delete(productOptionGroupEntity)
                .where(productOptionGroupEntity.productEntity.id.eq(productId))
                .execute();
    }

    @Override
    public Optional<ProductOptionGroupEntity> findByIdAndProductId(Long id, Long productId) {
        return Optional.ofNullable(qf.select(productOptionGroupEntity)
                .from(productOptionGroupEntity)
                .where(
                        productOptionGroupEntity.id.eq(id),
                        productOptionGroupEntity.productEntity.id.eq(productId)
                )
                .fetchOne());
    }
}
