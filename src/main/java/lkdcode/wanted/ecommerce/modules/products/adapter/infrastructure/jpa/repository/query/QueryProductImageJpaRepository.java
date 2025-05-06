package lkdcode.wanted.ecommerce.modules.products.adapter.infrastructure.jpa.repository.query;

import lkdcode.wanted.ecommerce.modules.products.adapter.infrastructure.jpa.entity.ProductImageJpaEntity;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface QueryProductImageJpaRepository extends Repository<ProductImageJpaEntity, Long> {
    List<ProductImageJpaEntity> findAllByProduct_id(Long productId);
}
