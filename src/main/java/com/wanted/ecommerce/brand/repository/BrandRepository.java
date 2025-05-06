package com.wanted.ecommerce.brand.repository;

import com.wanted.ecommerce.brand.domain.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BrandRepository extends JpaRepository<Brand, Long> {

}
