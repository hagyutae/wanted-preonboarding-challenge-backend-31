package com.example.wanted_preonboarding_challenge_backend_31.domain.repository.category;

import com.example.wanted_preonboarding_challenge_backend_31.domain.model.category.Category;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    List<Category> findAllByLevel(int level);

    @Query(value = """
            WITH RECURSIVE category_tree AS (
                        SELECT id
                        FROM categories
                        WHERE id = :categoryId
                        UNION ALL
                        SELECT c.id
                        FROM categories c
                        JOIN category_tree ct ON c.parent_id = ct.id
                    )
                    SELECT id FROM category_tree
            """, nativeQuery = true)
    List<Long> findAllChildCategoryIds(@Param("categoryId") Long categoryId);
}
