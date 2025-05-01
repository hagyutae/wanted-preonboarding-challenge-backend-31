package com.wanted.mono.domain.tag.repository;

import com.wanted.mono.domain.tag.entity.ProductTag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductTagRepository extends JpaRepository<ProductTag, Long> {
}
