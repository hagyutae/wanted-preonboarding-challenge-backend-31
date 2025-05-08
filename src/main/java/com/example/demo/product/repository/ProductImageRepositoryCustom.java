package com.example.demo.product.repository;

import com.example.demo.product.entity.ProductImageEntity;

import java.util.Collection;
import java.util.List;

public interface ProductImageRepositoryCustom {
    List<ProductImageEntity> findAllByProductIdsAndIsPrimary(Collection<Long> productIds);
    List<ProductImageEntity> findAllByProductIds(Collection<Long> productIds);
    Long deleteByProductId(Long productId);
}
