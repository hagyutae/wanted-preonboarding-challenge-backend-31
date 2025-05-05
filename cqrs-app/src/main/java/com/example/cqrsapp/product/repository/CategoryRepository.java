package com.example.cqrsapp.product.repository;

import com.example.cqrsapp.product.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findByLevelGreaterThanEqual(int level);

    List<Category> findByIdIn(List<Long> ids);
}
