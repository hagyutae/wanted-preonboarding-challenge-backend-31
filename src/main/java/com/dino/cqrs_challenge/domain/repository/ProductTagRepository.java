package com.dino.cqrs_challenge.domain.repository;

import com.dino.cqrs_challenge.domain.entity.ProductTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface ProductTagRepository extends JpaRepository<ProductTag, Long> {

    @Modifying
    @Transactional
    @Query("DELETE FROM ProductTag pt WHERE pt.product.id = :productId")
    void deleteAllByProductId(Long productId);
}
