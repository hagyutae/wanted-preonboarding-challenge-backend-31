package wanted.domain.category.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wanted.domain.category.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
