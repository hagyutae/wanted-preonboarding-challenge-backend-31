package com.example.preonboarding.products.repository;

import com.example.preonboarding.products.domain.Products;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Products,Long> {
}
