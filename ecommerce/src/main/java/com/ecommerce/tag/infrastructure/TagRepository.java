package com.ecommerce.tag.infrastructure;

import com.ecommerce.tag.domain.Tag;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {

    Optional<Tag> findByName(String name);

    Optional<Tag> findBySlug(String slug);

    List<Tag> findByNameContaining(String keyword);

    @Query("SELECT t FROM Tag t WHERE t.name IN :names")
    List<Tag> findByNames(@Param("names") List<String> names);

    @Query("SELECT t FROM Tag t WHERE t.id IN :ids")
    List<Tag> findByIds(@Param("ids") List<Long> ids);

    @Query("SELECT DISTINCT t FROM Tag t " +
            "JOIN ProductTag pt ON t.id = pt.tag.id " +
            "WHERE pt.product.id = :productId")
    List<Tag> findTagsByProductId(@Param("productId") Long productId);

    @Query(value = "SELECT t.* FROM tags t " +
            "JOIN product_tags pt ON t.id = pt.tag_id " +
            "GROUP BY t.id " +
            "ORDER BY COUNT(pt.product_id) DESC " +
            "LIMIT :limit",
            nativeQuery = true)
    List<Tag> findPopularTags(@Param("limit") int limit);

    @Query(value = "SELECT t.* FROM tags t " +
            "LEFT JOIN product_tags pt ON t.id = pt.tag_id " +
            "WHERE pt.id IS NULL",
            nativeQuery = true)
    List<Tag> findUnusedTags();
}
