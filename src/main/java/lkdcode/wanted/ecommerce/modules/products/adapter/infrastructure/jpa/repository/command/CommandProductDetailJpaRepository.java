package lkdcode.wanted.ecommerce.modules.products.adapter.infrastructure.jpa.repository.command;

import lkdcode.wanted.ecommerce.modules.products.adapter.infrastructure.jpa.entity.ProductDetailJpaEntity;
import org.springframework.data.repository.Repository;

public interface CommandProductDetailJpaRepository extends Repository<ProductDetailJpaEntity, Long> {
    void save(ProductDetailJpaEntity entity);
}
