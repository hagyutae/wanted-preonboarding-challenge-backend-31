package com.example.demo.productoption.repository;

import com.example.demo.productoption.dto.ProductStock;
import com.example.demo.productoption.entity.ProductOptionEntity;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface ProductOptionRepositoryCustom {
    List<ProductStock> findAllByProductIds(Collection<Long> productIds);
    
    List<ProductOptionEntity> findAllByProductId(Long productId);

    Optional<ProductOptionEntity> findByIdAndProductId(Long id, Long productId);
}
