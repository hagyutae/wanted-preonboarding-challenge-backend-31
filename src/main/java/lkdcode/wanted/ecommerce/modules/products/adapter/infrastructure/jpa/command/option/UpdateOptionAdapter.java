package lkdcode.wanted.ecommerce.modules.products.adapter.infrastructure.jpa.command.option;

import lkdcode.wanted.ecommerce.modules.products.adapter.infrastructure.jpa.repository.query.QueryProductOptionJpaRepository;
import lkdcode.wanted.ecommerce.modules.products.application.ports.out.option.command.UpdateOptionOutPort;
import lkdcode.wanted.ecommerce.modules.products.application.usecase.option.command.CommandOptionResult;
import lkdcode.wanted.ecommerce.modules.products.domain.model.option.UpdateOptionModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateOptionAdapter implements UpdateOptionOutPort {
    private final QueryProductOptionJpaRepository queryProductOptionJpaRepository;
    private final CommandProductOptionMapper mapper;

    @Override
    public CommandOptionResult update(UpdateOptionModel model) {
        final var target = queryProductOptionJpaRepository.loadById(model.optionId().value());
        target.update(model);

        return mapper.convert(target);
    }
}
