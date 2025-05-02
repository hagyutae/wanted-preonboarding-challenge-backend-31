package com.dino.cqrs_challenge.domain.repository;

import com.dino.cqrs_challenge.domain.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long>, ProductRepositoryCustom {
}
