package com.example.demo.productoption.repository;

import com.example.demo.productoption.dto.ProductStock;
import com.example.demo.productoption.dto.QProductStock;
import com.example.demo.productoption.entity.ProductOptionEntity;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static com.example.demo.productoption.entity.QProductOptionEntity.productOptionEntity;
import static com.example.demo.productoption.entity.QProductOptionGroupEntity.productOptionGroupEntity;

@Repository
@RequiredArgsConstructor
public class ProductOptionRepositoryCustomImpl implements ProductOptionRepositoryCustom{
    private final JPAQueryFactory qf;

    public List<ProductStock> findAllByProductIds(Collection<Long> productIds) {
        return qf.select(new QProductStock(
                        productOptionEntity.productOptionGroupEntity.productEntity.id,
                        productOptionEntity.stock.sum()
                ))
                .from(productOptionEntity)
                .where(productOptionEntity.productOptionGroupEntity.productEntity.id.in(productIds))
                .groupBy(productOptionEntity.productOptionGroupEntity.productEntity.id)
                .fetch();
    }

    public List<ProductOptionEntity> findAllByProductId(Long productId) {
        return qf.select(productOptionEntity)
                .from(productOptionEntity)
                .leftJoin(productOptionEntity.productOptionGroupEntity, productOptionGroupEntity).fetchJoin()
                .where(productOptionEntity.productOptionGroupEntity.productEntity.id.eq(productId))
                .fetch();
    }

    @Override
    public Optional<ProductOptionEntity> findByIdAndProductId(Long id, Long productId) {
        return Optional.ofNullable(qf.select(productOptionEntity)
                .from(productOptionEntity)
                .leftJoin(productOptionEntity.productOptionGroupEntity, productOptionGroupEntity).fetchJoin()
                .where(
                        productOptionEntity.id.eq(id),
                        productOptionEntity.productOptionGroupEntity.productEntity.id.eq(productId)
                )
                .fetchOne());
    }
}
