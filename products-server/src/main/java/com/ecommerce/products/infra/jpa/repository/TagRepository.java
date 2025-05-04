package com.ecommerce.products.infra.jpa.repository;

import com.ecommerce.products.infra.jpa.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long> {
}