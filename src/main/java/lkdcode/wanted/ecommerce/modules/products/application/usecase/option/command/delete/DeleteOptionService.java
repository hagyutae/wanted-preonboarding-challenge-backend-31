package lkdcode.wanted.ecommerce.modules.products.application.usecase.option.command.delete;

import lkdcode.wanted.ecommerce.modules.products.application.ports.out.option.command.DeleteOptionOutPort;
import lkdcode.wanted.ecommerce.modules.products.application.ports.out.validator.OptionValidator;
import lkdcode.wanted.ecommerce.modules.products.domain.entity.ProductId;
import lkdcode.wanted.ecommerce.modules.products.domain.entity.ProductOptionId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class DeleteOptionService {
    private final OptionValidator optionValidator;
    private final DeleteOptionOutPort deleteOptionOutPort;

    public void delete(final ProductId productId, final ProductOptionId optionId) {
        DeleteOptionUsecase.execute(productId, optionId)
            .validateOptionInProduct(optionValidator::validOption)
            .delete(deleteOptionOutPort::delete);
    }
}