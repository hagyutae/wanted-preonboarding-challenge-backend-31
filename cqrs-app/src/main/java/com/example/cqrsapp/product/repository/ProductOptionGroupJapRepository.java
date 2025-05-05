package com.example.cqrsapp.product.repository;

import com.example.cqrsapp.product.domain.ProductOptionGroup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductOptionGroupJapRepository extends JpaRepository<ProductOptionGroup, Long> {
}
