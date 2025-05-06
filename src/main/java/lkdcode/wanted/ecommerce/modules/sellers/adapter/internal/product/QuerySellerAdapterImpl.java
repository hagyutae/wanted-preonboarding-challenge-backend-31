package lkdcode.wanted.ecommerce.modules.sellers.adapter.internal.product;

import lkdcode.wanted.ecommerce.modules.products.adapter.external.seller.QuerySellerAdapter;
import lkdcode.wanted.ecommerce.modules.sellers.adapter.infrastructure.jpa.entity.SellerJpaEntity;
import lkdcode.wanted.ecommerce.modules.sellers.adapter.infrastructure.jpa.repository.query.QuerySellerJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QuerySellerAdapterImpl implements QuerySellerAdapter {
    private final QuerySellerJpaRepository repository;

    @Override
    public SellerJpaEntity load(Long id) {
        return repository.findById(id)
            .orElse(null);
    }
}