package lkdcode.wanted.ecommerce.modules.brand.adapter.infrastructure.jpa.repository.command;


import lkdcode.wanted.ecommerce.modules.brand.adapter.infrastructure.jpa.entity.BrandJpaEntity;
import org.springframework.data.repository.Repository;

public interface CommandBrandJpaRepository extends Repository<BrandJpaEntity, Long> {
}