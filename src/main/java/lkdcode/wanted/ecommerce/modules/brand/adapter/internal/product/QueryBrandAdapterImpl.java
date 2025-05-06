package lkdcode.wanted.ecommerce.modules.brand.adapter.internal.product;

import lkdcode.wanted.ecommerce.modules.brand.adapter.infrastructure.jpa.entity.BrandJpaEntity;
import lkdcode.wanted.ecommerce.modules.brand.adapter.infrastructure.jpa.repository.query.QueryBrandJpaRepository;
import lkdcode.wanted.ecommerce.modules.products.adapter.external.brand.QueryBrandAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QueryBrandAdapterImpl implements QueryBrandAdapter {

    private final QueryBrandJpaRepository repository;

    @Override
    public BrandJpaEntity load(Long id) {
        return repository.findById(id)
            .orElse(null);
    }
}
