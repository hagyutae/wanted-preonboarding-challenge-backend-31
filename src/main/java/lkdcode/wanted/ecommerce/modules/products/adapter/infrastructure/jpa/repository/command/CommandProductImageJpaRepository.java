package lkdcode.wanted.ecommerce.modules.products.adapter.infrastructure.jpa.repository.command;

import lkdcode.wanted.ecommerce.modules.products.adapter.infrastructure.jpa.entity.ProductImageJpaEntity;
import org.springframework.data.repository.Repository;

public interface CommandProductImageJpaRepository extends Repository<ProductImageJpaEntity, Long> {
    void save(ProductImageJpaEntity entity);

    void deleteAllByProduct_id(Long productId);
}
