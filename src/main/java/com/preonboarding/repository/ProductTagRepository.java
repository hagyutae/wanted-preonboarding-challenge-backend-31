package com.preonboarding.repository;

import com.preonboarding.domain.ProductTag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductTagRepository extends JpaRepository<ProductTag,Long> {
}
