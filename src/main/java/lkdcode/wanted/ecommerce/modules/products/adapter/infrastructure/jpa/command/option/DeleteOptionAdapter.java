package lkdcode.wanted.ecommerce.modules.products.adapter.infrastructure.jpa.command.option;

import lkdcode.wanted.ecommerce.modules.products.adapter.infrastructure.jpa.repository.command.CommandProductOptionJpaRepository;
import lkdcode.wanted.ecommerce.modules.products.adapter.infrastructure.jpa.repository.query.QueryProductOptionJpaRepository;
import lkdcode.wanted.ecommerce.modules.products.application.ports.out.option.command.DeleteOptionOutPort;
import lkdcode.wanted.ecommerce.modules.products.domain.entity.ProductOptionId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteOptionAdapter implements DeleteOptionOutPort {
    private final QueryProductOptionJpaRepository queryProductOptionJpaRepository;
    private final CommandProductOptionJpaRepository commandProductOptionJpaRepository;

    @Override
    public void delete(ProductOptionId target) {
        final var entity = queryProductOptionJpaRepository.loadById(target.value());
        commandProductOptionJpaRepository.delete(entity);
    }
}