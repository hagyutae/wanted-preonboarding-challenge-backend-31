package lkdcode.wanted.ecommerce.modules.products.adapter.external.category;

import lkdcode.wanted.ecommerce.modules.category.adapter.infrastructure.jpa.entity.CategoryJpaEntity;

public interface QueryCategoryAdapter {
    CategoryJpaEntity load(Long id);
}
