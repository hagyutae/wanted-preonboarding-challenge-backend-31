package com.wanted_preonboarding_challenge_backend.eCommerce.respository;

import com.wanted_preonboarding_challenge_backend.eCommerce.domain.ProductTag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductTagRepository extends JpaRepository<ProductTag, Long> {
    void deleteByProductId(Long productId);
    List<ProductTag> findByProductId(Long id);
}
