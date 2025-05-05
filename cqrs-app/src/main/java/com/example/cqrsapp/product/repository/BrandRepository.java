package com.example.cqrsapp.product.repository;

import com.example.cqrsapp.product.domain.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BrandRepository extends JpaRepository<Brand, Long> {
}
