package lkdcode.wanted.ecommerce.modules.tag.adapter.infrastructure.jpa.repository.query;

import lkdcode.wanted.ecommerce.modules.tag.adapter.infrastructure.jpa.entity.TagJpaEntity;
import org.springframework.data.repository.Repository;

import java.util.Optional;

public interface QueryTagJpaRepository extends Repository<TagJpaEntity, Long> {
    Optional<TagJpaEntity> findById(Long id);
}
