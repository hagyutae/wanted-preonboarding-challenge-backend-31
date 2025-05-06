package lkdcode.wanted.ecommerce.modules.products.adapter.infrastructure.jpa.command.option;

import lkdcode.wanted.ecommerce.modules.products.adapter.infrastructure.jpa.repository.command.CommandProductOptionJpaRepository;
import lkdcode.wanted.ecommerce.modules.products.adapter.infrastructure.jpa.repository.query.QueryProductOptionGroupJpaRepository;
import lkdcode.wanted.ecommerce.modules.products.application.ports.out.option.command.AddOptionOutPort;
import lkdcode.wanted.ecommerce.modules.products.application.usecase.option.command.CommandOptionResult;
import lkdcode.wanted.ecommerce.modules.products.domain.model.option.AddOptionModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddOptionAdapter implements AddOptionOutPort {
    private final CommandProductOptionJpaRepository commandOptionRepository;
    private final QueryProductOptionGroupJpaRepository queryProductOptionGroupJpaRepository;
    private final CommandProductOptionMapper mapper;

    @Override
    public CommandOptionResult add(AddOptionModel model) {
        final var groupEntity = queryProductOptionGroupJpaRepository.loadById(model.optionGroupId().value());
        final var entity = mapper.convert(groupEntity, model);
        final var saved = commandOptionRepository.save(entity);

        return mapper.convert(saved);
    }
}