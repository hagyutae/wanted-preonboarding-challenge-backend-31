package lkdcode.wanted.ecommerce.modules.sellers.adapter.infrastructure.jpa.repository;

import lkdcode.wanted.ecommerce.modules.sellers.adapter.infrastructure.jpa.entity.SellerJpaEntity;
import org.springframework.data.repository.Repository;

public interface SellerJpaRepository extends Repository<SellerJpaEntity, Long> {
}
