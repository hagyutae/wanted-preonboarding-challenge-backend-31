package com.wanted_preonboarding_challenge_backend.eCommerce.respository;

import com.wanted_preonboarding_challenge_backend.eCommerce.domain.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BrandRepository extends JpaRepository<Brand, Long> {
}
