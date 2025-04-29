package com.example.wanted_preonboarding_challenge_backend_31.domain.repository.product;

import com.example.wanted_preonboarding_challenge_backend_31.domain.model.product.ProductOptionGroup;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductOptionGroupRepository extends JpaRepository<ProductOptionGroup, Long> {

    void deleteAllByProductId(Long productId);

    Optional<ProductOptionGroup> findByIdAndProductId(Long id, Long productId);

    List<ProductOptionGroup> findAllByProductId(Long productId);
}
