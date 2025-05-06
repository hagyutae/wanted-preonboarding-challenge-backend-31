package lkdcode.wanted.ecommerce.modules.brand.adapter.infrastructure.jpa.repository.query;


import lkdcode.wanted.ecommerce.modules.brand.adapter.infrastructure.jpa.entity.BrandJpaEntity;
import org.springframework.data.repository.Repository;

import java.util.Optional;

public interface QueryBrandJpaRepository extends Repository<BrandJpaEntity, Long> {
    Optional<BrandJpaEntity> findById(Long id);
}