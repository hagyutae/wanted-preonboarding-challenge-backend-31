package com.ecommerce.brand.infrastructure;

import com.ecommerce.brand.domain.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BrandRepository extends JpaRepository<Brand, Long> {
}
