package lkdcode.wanted.ecommerce.modules.products.adapter.infrastructure.jpa.command;

import lkdcode.wanted.ecommerce.modules.products.adapter.infrastructure.jpa.repository.query.QueryProductJpaRepository;
import lkdcode.wanted.ecommerce.modules.products.application.ports.out.command.DeleteProductOutPort;
import lkdcode.wanted.ecommerce.modules.products.domain.entity.ProductId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteProductAdapter implements DeleteProductOutPort {
    private final QueryProductJpaRepository queryRepository;

    @Override
    public void delete(ProductId id) {
        final var target = queryRepository.loadById(id.value());
        target.delete();
    }
}