package lkdcode.wanted.ecommerce.modules.products.adapter.external.brand;

import lkdcode.wanted.ecommerce.modules.brand.adapter.infrastructure.jpa.entity.BrandJpaEntity;

public interface QueryBrandAdapter {
    BrandJpaEntity load(Long id);
}
