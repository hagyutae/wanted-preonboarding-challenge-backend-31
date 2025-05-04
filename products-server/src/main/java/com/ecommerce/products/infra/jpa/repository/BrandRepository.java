package com.ecommerce.products.infra.jpa.repository;

import com.ecommerce.products.infra.jpa.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BrandRepository extends JpaRepository<Brand, Long> {
}