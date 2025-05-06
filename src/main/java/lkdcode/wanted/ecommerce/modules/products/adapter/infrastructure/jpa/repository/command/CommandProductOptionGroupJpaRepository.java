package lkdcode.wanted.ecommerce.modules.products.adapter.infrastructure.jpa.repository.command;

import lkdcode.wanted.ecommerce.modules.products.adapter.infrastructure.jpa.entity.ProductOptionGroupJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommandProductOptionGroupJpaRepository extends JpaRepository<ProductOptionGroupJpaEntity, Long> {
    void deleteAllByProduct_id(Long productId);
}
