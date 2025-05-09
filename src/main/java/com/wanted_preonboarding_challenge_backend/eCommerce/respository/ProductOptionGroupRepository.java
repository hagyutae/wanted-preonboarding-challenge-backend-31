package com.wanted_preonboarding_challenge_backend.eCommerce.respository;

import com.wanted_preonboarding_challenge_backend.eCommerce.domain.Product;
import com.wanted_preonboarding_challenge_backend.eCommerce.domain.ProductOptionGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductOptionGroupRepository extends JpaRepository<ProductOptionGroup, Long> {
    List<ProductOptionGroup> findByProductId(Long id);
    void  deleteByProductId(Long productId);

}
