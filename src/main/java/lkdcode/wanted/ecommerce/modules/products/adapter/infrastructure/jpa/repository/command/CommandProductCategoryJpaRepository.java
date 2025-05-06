package lkdcode.wanted.ecommerce.modules.products.adapter.infrastructure.jpa.repository.command;

import lkdcode.wanted.ecommerce.modules.products.adapter.infrastructure.jpa.entity.ProductCategoryJpaEntity;
import org.springframework.data.repository.Repository;

public interface CommandProductCategoryJpaRepository extends Repository<ProductCategoryJpaEntity, Long> {
    void save(ProductCategoryJpaEntity entity);

    void deleteAllByProduct_Id(Long productId);
}