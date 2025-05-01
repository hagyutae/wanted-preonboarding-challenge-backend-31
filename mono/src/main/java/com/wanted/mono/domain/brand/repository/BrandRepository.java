package com.wanted.mono.domain.brand.repository;

import com.wanted.mono.domain.brand.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BrandRepository extends JpaRepository<Brand, Long> {
}
