package com.wanted_preonboarding_challenge_backend.eCommerce.respository;

import com.wanted_preonboarding_challenge_backend.eCommerce.domain.ProductOption;
import com.wanted_preonboarding_challenge_backend.eCommerce.domain.ProductOptionGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductOptionRepository extends JpaRepository<ProductOption, Long> {
    List<ProductOptionGroup> findByProductOptionGroup(ProductOptionGroup productOptionGroup);
}
