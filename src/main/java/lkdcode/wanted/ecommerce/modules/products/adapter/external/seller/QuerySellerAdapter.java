package lkdcode.wanted.ecommerce.modules.products.adapter.external.seller;

import lkdcode.wanted.ecommerce.modules.sellers.adapter.infrastructure.jpa.entity.SellerJpaEntity;

public interface QuerySellerAdapter {
    SellerJpaEntity load(Long id);
}
