package com.example.demo.product.repository;

import com.example.demo.product.entity.ProductTagEntity;

import java.util.List;

public interface ProductTagRepositoryCustom {
    List<ProductTagEntity> findAllByProductId(Long productId);
    Long deleteByProductId(Long productId);
}
