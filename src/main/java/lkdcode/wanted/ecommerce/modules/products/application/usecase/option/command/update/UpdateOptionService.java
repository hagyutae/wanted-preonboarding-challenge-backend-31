package lkdcode.wanted.ecommerce.modules.products.application.usecase.option.command.update;

import lkdcode.wanted.ecommerce.modules.products.application.ports.out.option.command.UpdateOptionOutPort;
import lkdcode.wanted.ecommerce.modules.products.application.ports.out.validator.OptionValidator;
import lkdcode.wanted.ecommerce.modules.products.application.usecase.option.command.CommandOptionResult;
import lkdcode.wanted.ecommerce.modules.products.domain.entity.ProductId;
import lkdcode.wanted.ecommerce.modules.products.domain.model.option.UpdateOptionModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UpdateOptionService {
    private final OptionValidator optionValidator;
    private final UpdateOptionOutPort port;

    public CommandOptionResult update(ProductId productId, UpdateOptionModel model) {
        return UpdateOptionUsecase.execute(productId, model)
            .validAuthority(optionValidator::validOption)
            .update(port::update)
            .done();
    }
}