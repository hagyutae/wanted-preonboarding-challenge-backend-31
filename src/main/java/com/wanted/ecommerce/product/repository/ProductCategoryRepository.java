package com.wanted.ecommerce.product.repository;

import com.wanted.ecommerce.product.domain.ProductCategory;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {

    List<ProductCategory> findByCategoryIdAndProductId(Long categoryId, Long productId);
}
