package com.ecommerce.product.infrastructure;

import com.ecommerce.product.domain.Category;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    Optional<Category> findBySlug(String slug);

    List<Category> findByParentIdIsNull();

    List<Category> findByParentId(Long parentId);

    List<Category> findByLevel(Integer level);

    @Query("SELECT c FROM Category c WHERE c.parent.id = :parentId")
    List<Category> findSubcategories(@Param("parentId") Long parentId);

    @Query("SELECT DISTINCT c FROM Category c " +
            "JOIN ProductCategory pc ON pc.category.id = c.id " +
            "JOIN Product p ON p.id = pc.product.id " +
            "WHERE p.id = :productId")
    List<Category> findCategoriesByProductId(@Param("productId") Long productId);

    @Query("SELECT c FROM Category c WHERE c.name LIKE %:keyword% OR c.description LIKE %:keyword%")
    List<Category> searchCategories(@Param("keyword") String keyword);

    // 전체 카테고리 계층 구조 조회 (재귀 쿼리)
    @Query(value = "WITH RECURSIVE category_tree AS (" +
            "SELECT id, name, parent_id, level, 1 as depth, ARRAY[id] as path " +
            "FROM categories " +
            "WHERE parent_id IS NULL " +
            "UNION ALL " +
            "SELECT c.id, c.name, c.parent_id, c.level, ct.depth + 1, ct.path || c.id " +
            "FROM categories c " +
            "JOIN category_tree ct ON c.parent_id = ct.id " +
            ") " +
            "SELECT id, name, parent_id, level FROM category_tree ORDER BY path",
            nativeQuery = true)
    List<Object[]> getCategoryHierarchy();

    @Query(value = "SELECT c.* FROM categories c " +
            "JOIN product_categories pc ON c.id = pc.category_id " +
            "JOIN products p ON pc.product_id = p.id " +
            "GROUP BY c.id " +
            "ORDER BY COUNT(p.id) DESC " +
            "LIMIT :limit",
            nativeQuery = true)
    List<Category> findMostPopularCategories(@Param("limit") int limit);

    @Query("SELECT c FROM Category c WHERE c.imageUrl IS NOT NULL")
    List<Category> findCategoriesWithImages();
}
