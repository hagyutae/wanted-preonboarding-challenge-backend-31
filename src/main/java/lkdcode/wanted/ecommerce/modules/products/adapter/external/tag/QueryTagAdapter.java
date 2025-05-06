package lkdcode.wanted.ecommerce.modules.products.adapter.external.tag;

import lkdcode.wanted.ecommerce.modules.tag.adapter.infrastructure.jpa.entity.TagJpaEntity;

public interface QueryTagAdapter {
    TagJpaEntity load(Long id);
}
