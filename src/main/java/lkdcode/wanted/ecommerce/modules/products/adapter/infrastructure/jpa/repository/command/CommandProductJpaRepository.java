package lkdcode.wanted.ecommerce.modules.products.adapter.infrastructure.jpa.repository.command;

import lkdcode.wanted.ecommerce.modules.products.adapter.infrastructure.jpa.entity.ProductJpaEntity;
import org.springframework.data.repository.Repository;

public interface CommandProductJpaRepository extends Repository<ProductJpaEntity, Long> {
    ProductJpaEntity save(ProductJpaEntity entity);
}
