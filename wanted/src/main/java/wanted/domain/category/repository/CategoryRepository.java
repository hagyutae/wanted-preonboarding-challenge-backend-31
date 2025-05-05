package wanted.domain.category.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wanted.domain.category.entity.Category;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findByLevel(int level);
}
