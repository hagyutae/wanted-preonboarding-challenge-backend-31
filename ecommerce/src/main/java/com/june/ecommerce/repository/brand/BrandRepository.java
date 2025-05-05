package com.june.ecommerce.repository.brand;

import com.june.ecommerce.domain.brand.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BrandRepository  extends JpaRepository<Brand, Integer> {
}
