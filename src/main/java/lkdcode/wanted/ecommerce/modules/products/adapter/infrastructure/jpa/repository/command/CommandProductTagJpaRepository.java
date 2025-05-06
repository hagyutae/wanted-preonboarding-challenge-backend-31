package lkdcode.wanted.ecommerce.modules.products.adapter.infrastructure.jpa.repository.command;

import lkdcode.wanted.ecommerce.modules.products.adapter.infrastructure.jpa.entity.ProductTagJpaEntity;
import org.springframework.data.repository.Repository;

public interface CommandProductTagJpaRepository extends Repository<ProductTagJpaEntity, Long> {
    void save(ProductTagJpaEntity entity);

    void deleteAllByProduct_id(Long productId);
}
