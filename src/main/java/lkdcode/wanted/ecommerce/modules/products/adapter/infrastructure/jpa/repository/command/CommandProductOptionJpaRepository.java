package lkdcode.wanted.ecommerce.modules.products.adapter.infrastructure.jpa.repository.command;

import lkdcode.wanted.ecommerce.modules.products.adapter.infrastructure.jpa.entity.ProductOptionJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommandProductOptionJpaRepository extends JpaRepository<ProductOptionJpaEntity, Long> {
}