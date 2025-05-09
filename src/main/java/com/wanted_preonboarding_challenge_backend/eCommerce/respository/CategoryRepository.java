package com.wanted_preonboarding_challenge_backend.eCommerce.respository;

import com.wanted_preonboarding_challenge_backend.eCommerce.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long>, CategoryRepositoryCustom {
    @Query(value = """
        WITH RECURSIVE subcategories AS (
            SELECT id FROM category WHERE id = :categoryId
            UNION ALL
            SELECT c.id
            FROM category c
            INNER JOIN subcategories sc ON c.parent_id = sc.id
        )
        SELECT id FROM subcategories;
    """, nativeQuery = true)
    List<Long> findAllDescendantIds(@Param("categoryId") Long categoryId);

    List<Category> findByLevel(int level);
}
