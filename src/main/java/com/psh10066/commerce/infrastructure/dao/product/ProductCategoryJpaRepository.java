package com.psh10066.commerce.infrastructure.dao.product;

import com.psh10066.commerce.domain.model.product.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductCategoryJpaRepository extends JpaRepository<ProductCategory, Long> {

    List<ProductCategory> findAllByProductId(Long productId);

    void deleteAllByProductId(Long productId);
}
