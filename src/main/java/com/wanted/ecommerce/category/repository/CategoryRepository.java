package com.wanted.ecommerce.category.repository;

import com.wanted.ecommerce.category.domain.Category;
import com.wanted.ecommerce.product.repository.ProductSearchRepository;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long>, ProductSearchRepository {

    List<Category> findAllByLevel(Integer level);

    List<Category> findAllByParentId(Long parentId);
}
