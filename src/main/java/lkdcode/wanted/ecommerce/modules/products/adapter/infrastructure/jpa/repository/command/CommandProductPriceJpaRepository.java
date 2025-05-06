package lkdcode.wanted.ecommerce.modules.products.adapter.infrastructure.jpa.repository.command;

import lkdcode.wanted.ecommerce.modules.products.adapter.infrastructure.jpa.entity.ProductPriceJpaEntity;
import org.springframework.data.repository.Repository;

public interface CommandProductPriceJpaRepository extends Repository<ProductPriceJpaEntity, Long> {
    void save(ProductPriceJpaEntity entity);

    void deleteAllByProduct_id(Long productId);
}
