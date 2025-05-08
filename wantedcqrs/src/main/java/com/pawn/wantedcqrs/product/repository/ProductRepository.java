package com.pawn.wantedcqrs.product.repository;

import com.pawn.wantedcqrs.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findBySlug(String slug);
}
