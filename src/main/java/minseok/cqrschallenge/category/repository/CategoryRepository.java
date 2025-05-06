package minseok.cqrschallenge.category.repository;

import java.util.List;
import minseok.cqrschallenge.category.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    
    @Query("SELECT c FROM Category c WHERE c.level = :level")
    List<Category> findByLevel(@Param("level") Integer level);
    
    @Query("SELECT c FROM Category c WHERE c.parent IS NULL ORDER BY c.id")
    List<Category> findAllRootCategories();
    
    @Query("SELECT c FROM Category c LEFT JOIN FETCH c.parent WHERE c.id = :id")
    Category findByIdWithParent(@Param("id") Long id);
}