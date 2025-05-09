package com.wanted_preonboarding_challenge_backend.eCommerce.respository;

import com.wanted_preonboarding_challenge_backend.eCommerce.domain.ProductPrice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductPriceRepository extends JpaRepository <ProductPrice, Long>{
}
