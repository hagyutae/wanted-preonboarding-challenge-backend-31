package lkdcode.wanted.ecommerce.modules.category.adapter.infrastructure.jpa.repository.command;

import lkdcode.wanted.ecommerce.modules.category.adapter.infrastructure.jpa.entity.CategoryJpaEntity;
import org.springframework.data.repository.Repository;

public interface CommandCategoryJpaRepository extends Repository<CategoryJpaEntity, Long> {

}