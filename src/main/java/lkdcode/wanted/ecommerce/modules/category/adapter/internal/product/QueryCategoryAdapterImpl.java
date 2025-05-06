package lkdcode.wanted.ecommerce.modules.category.adapter.internal.product;

import lkdcode.wanted.ecommerce.modules.category.adapter.infrastructure.jpa.entity.CategoryJpaEntity;
import lkdcode.wanted.ecommerce.modules.category.adapter.infrastructure.jpa.repository.query.QueryCategoryJpaRepository;
import lkdcode.wanted.ecommerce.modules.products.adapter.external.category.QueryCategoryAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QueryCategoryAdapterImpl implements QueryCategoryAdapter {
    private final QueryCategoryJpaRepository repository;

    @Override
    public CategoryJpaEntity load(Long id) {
        return repository.findById(id)
            .orElse(null);
    }
}
