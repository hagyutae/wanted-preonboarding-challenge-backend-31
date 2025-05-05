package com.preonboarding.challenge.service.repository;

import com.preonboarding.challenge.service.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BrandRepository extends JpaRepository<Brand, Long> {
}