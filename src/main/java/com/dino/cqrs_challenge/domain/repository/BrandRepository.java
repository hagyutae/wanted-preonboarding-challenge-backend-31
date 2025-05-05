package com.dino.cqrs_challenge.domain.repository;

import com.dino.cqrs_challenge.domain.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BrandRepository extends JpaRepository<Brand, Long> {
}
