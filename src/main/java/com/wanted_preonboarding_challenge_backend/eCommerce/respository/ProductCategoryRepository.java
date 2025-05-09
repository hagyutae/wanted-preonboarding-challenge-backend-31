package com.wanted_preonboarding_challenge_backend.eCommerce.respository;

import com.wanted_preonboarding_challenge_backend.eCommerce.domain.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {
    void deleteByProductId(Long productId);
    List<ProductCategory> findByProductId(Long productId);
}
