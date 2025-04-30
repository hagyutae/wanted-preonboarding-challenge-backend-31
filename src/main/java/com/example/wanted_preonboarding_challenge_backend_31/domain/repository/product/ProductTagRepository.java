package com.example.wanted_preonboarding_challenge_backend_31.domain.repository.product;

import com.example.wanted_preonboarding_challenge_backend_31.domain.model.product.ProductTag;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductTagRepository extends JpaRepository<ProductTag, Long> {

    void deleteAllByProductId(Long productId);

    @Query("select pt.id from ProductTag pt where pt.product.id = :productId")
    List<Long> findAllIdsByProductId(@Param("productId") Long productId);
}
