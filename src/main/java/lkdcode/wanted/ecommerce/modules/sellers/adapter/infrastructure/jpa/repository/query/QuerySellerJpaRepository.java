package lkdcode.wanted.ecommerce.modules.sellers.adapter.infrastructure.jpa.repository.query;

import lkdcode.wanted.ecommerce.modules.sellers.adapter.infrastructure.jpa.entity.SellerJpaEntity;
import org.springframework.data.repository.Repository;

import java.util.Optional;

public interface QuerySellerJpaRepository extends Repository<SellerJpaEntity, Long> {
    Optional<SellerJpaEntity> findById(Long id);
}
