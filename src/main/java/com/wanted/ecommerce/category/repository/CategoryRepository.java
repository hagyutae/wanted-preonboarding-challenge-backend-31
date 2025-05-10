package com.wanted.ecommerce.category.repository;

import com.wanted.ecommerce.category.domain.Category;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CategoryRepository extends JpaRepository<Category, Long>, CategorySearchRepository {

    List<Category> findAllByLevel(Integer level);

    List<Category> findAllByParentId(Long parentId);

    @Query(value = """
        WITH RECURSIVE category_hierarchy AS(
            SELECT * FROM categories
                WHERE id = :categoryId
                UNION ALL
                SELECT c.*
                FROM categories c
                INNER JOIN category_hierarchy ch ON c.parent_id = ch.id
        )
        SELECT * FROM category_hierarchy
        """, nativeQuery = true)
    List<Category> findAllIncludeDescendants(@Param("categoryId") Long categoryId);
}
