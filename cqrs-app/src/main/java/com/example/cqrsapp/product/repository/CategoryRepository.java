package com.example.cqrsapp.product.repository;

import com.example.cqrsapp.product.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long>, CategoryRepositoryCustom {
    List<Category> findByLevelGreaterThanEqual(int level);

    List<Category> findByIdIn(List<Long> ids);

    @Query(value = """
        WITH RECURSIVE category_tree AS (
            SELECT *
            FROM categories
            WHERE id = :categoryId
            UNION ALL
            SELECT c.*
            FROM categories c
            INNER JOIN category_tree ct ON c.parent_id = ct.id
        )
        SELECT *
        FROM category_tree
        """, nativeQuery = true)
    List<Category> findAllDescendants(@Param("categoryId") Long categoryId);
}
