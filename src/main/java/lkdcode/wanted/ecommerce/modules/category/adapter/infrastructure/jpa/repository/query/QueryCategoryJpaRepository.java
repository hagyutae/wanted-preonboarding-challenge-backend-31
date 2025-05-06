package lkdcode.wanted.ecommerce.modules.category.adapter.infrastructure.jpa.repository.query;

import lkdcode.wanted.ecommerce.modules.category.adapter.infrastructure.jpa.entity.CategoryJpaEntity;
import org.springframework.data.repository.Repository;

import java.util.Optional;

public interface QueryCategoryJpaRepository extends Repository<CategoryJpaEntity, Long> {
    Optional<CategoryJpaEntity> findById(Long id);
}