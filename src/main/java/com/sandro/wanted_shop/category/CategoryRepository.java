package com.sandro.wanted_shop.category;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    @EntityGraph(attributePaths = {"children", "children.children"})
    @Query("SELECT DISTINCT c FROM Category c WHERE c.level = 1")
    List<Category> findAllWithChildren();
}
