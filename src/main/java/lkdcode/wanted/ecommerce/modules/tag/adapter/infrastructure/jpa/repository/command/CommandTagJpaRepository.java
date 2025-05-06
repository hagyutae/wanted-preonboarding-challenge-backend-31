
package lkdcode.wanted.ecommerce.modules.tag.adapter.infrastructure.jpa.repository.command;

import lkdcode.wanted.ecommerce.modules.tag.adapter.infrastructure.jpa.entity.TagJpaEntity;
import org.springframework.data.repository.Repository;

public interface CommandTagJpaRepository extends Repository<TagJpaEntity, Long> {
}
