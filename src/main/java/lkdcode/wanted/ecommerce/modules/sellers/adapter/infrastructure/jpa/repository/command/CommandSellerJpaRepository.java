package lkdcode.wanted.ecommerce.modules.sellers.adapter.infrastructure.jpa.repository.command;

import lkdcode.wanted.ecommerce.modules.sellers.adapter.infrastructure.jpa.entity.SellerJpaEntity;
import org.springframework.data.repository.Repository;

public interface CommandSellerJpaRepository extends Repository<SellerJpaEntity, Long> {
}
