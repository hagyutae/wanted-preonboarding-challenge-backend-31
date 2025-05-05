package com.example.cqrsapp.product.repository;

import com.example.cqrsapp.product.domain.ProductTag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductTagRepository extends JpaRepository<ProductTag, Long> {
}
