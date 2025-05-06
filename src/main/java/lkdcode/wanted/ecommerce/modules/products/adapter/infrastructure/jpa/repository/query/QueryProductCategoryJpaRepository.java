package lkdcode.wanted.ecommerce.modules.products.adapter.infrastructure.jpa.repository.query;

import lkdcode.wanted.ecommerce.modules.products.adapter.infrastructure.jpa.entity.ProductCategoryJpaEntity;
import org.springframework.data.repository.Repository;

public interface QueryProductCategoryJpaRepository extends Repository<ProductCategoryJpaEntity, Long> {
}