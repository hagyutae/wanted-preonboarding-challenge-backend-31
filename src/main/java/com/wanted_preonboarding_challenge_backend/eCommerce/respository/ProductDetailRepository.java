package com.wanted_preonboarding_challenge_backend.eCommerce.respository;

import com.wanted_preonboarding_challenge_backend.eCommerce.domain.ProductDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductDetailRepository extends JpaRepository<ProductDetail, Long> {
}
