package com.psh10066.commerce.infrastructure.dao.brand;

import com.psh10066.commerce.domain.model.brand.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BrandJpaRepository extends JpaRepository<Brand, Long> {
}
