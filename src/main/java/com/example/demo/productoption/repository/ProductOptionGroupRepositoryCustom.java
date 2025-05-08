package com.example.demo.productoption.repository;


import com.example.demo.productoption.entity.ProductOptionGroupEntity;

import java.util.List;
import java.util.Optional;

public interface ProductOptionGroupRepositoryCustom {

    List<ProductOptionGroupEntity> findAllByProductId(Long productId);

    Long deleteByProductId(Long productId);

    Optional<ProductOptionGroupEntity> findByIdAndProductId(Long id, Long productId);
}
