package kr.co.wanted.backend31.repository.category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kr.co.wanted.backend31.common.model.category.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

}
