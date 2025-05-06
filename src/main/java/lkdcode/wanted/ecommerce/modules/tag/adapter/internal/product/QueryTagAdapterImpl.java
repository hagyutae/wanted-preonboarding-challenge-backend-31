package lkdcode.wanted.ecommerce.modules.tag.adapter.internal.product;

import lkdcode.wanted.ecommerce.modules.products.adapter.external.tag.QueryTagAdapter;
import lkdcode.wanted.ecommerce.modules.tag.adapter.infrastructure.jpa.entity.TagJpaEntity;
import lkdcode.wanted.ecommerce.modules.tag.adapter.infrastructure.jpa.repository.query.QueryTagJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QueryTagAdapterImpl implements QueryTagAdapter {
    private final QueryTagJpaRepository repository;

    @Override
    public TagJpaEntity load(Long id) {
        return repository.findById(id)
            .orElse(null);
    }
}