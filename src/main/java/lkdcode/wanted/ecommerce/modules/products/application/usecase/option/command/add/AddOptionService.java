package lkdcode.wanted.ecommerce.modules.products.application.usecase.option.command.add;

import lkdcode.wanted.ecommerce.modules.products.application.ports.out.option.command.AddOptionOutPort;
import lkdcode.wanted.ecommerce.modules.products.application.ports.out.validator.OptionValidator;
import lkdcode.wanted.ecommerce.modules.products.application.usecase.option.command.CommandOptionResult;
import lkdcode.wanted.ecommerce.modules.products.domain.entity.ProductId;
import lkdcode.wanted.ecommerce.modules.products.domain.model.option.AddOptionModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AddOptionService {
    private final OptionValidator optionValidator;
    private final AddOptionOutPort addOptionOutPort;

    public CommandOptionResult addOption(final ProductId productId, final AddOptionModel model) {
        return AddOptionUsecase.execute(productId, model)
            .validOptionGroupId(optionValidator::validOption)
            .addOption(addOptionOutPort::add)
            .done();
    }
}