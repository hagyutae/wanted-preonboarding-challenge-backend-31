package com.example.preonboarding.brands.repository;

import com.example.preonboarding.brands.domain.Brands;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BrandsRepository extends JpaRepository<Brands,Long> {
}