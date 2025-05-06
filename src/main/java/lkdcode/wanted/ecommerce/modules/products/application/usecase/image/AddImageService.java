package lkdcode.wanted.ecommerce.modules.products.application.usecase.image;

import lkdcode.wanted.ecommerce.modules.products.application.ports.out.image.CommandImageOutPort;
import lkdcode.wanted.ecommerce.modules.products.application.ports.out.validator.OptionValidator;
import lkdcode.wanted.ecommerce.modules.products.application.ports.out.validator.ProductValidator;
import lkdcode.wanted.ecommerce.modules.products.domain.model.image.AddImageModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AddImageService {
    private final ProductValidator productValidator;
    private final OptionValidator optionValidator;
    private final CommandImageOutPort commandImageOutPort;

    public AddImageResult addImage(final AddImageModel model) {
        return AddImageUsecase.execute(model)
            .validProductId(productValidator::existsProduct)
            .validateOptionInProduct(optionValidator::validOption)
            .unsetPrimary(commandImageOutPort::unsetPrimary)
            .shiftDisplayOrder(commandImageOutPort::shiftDisplayOrder)
            .add(commandImageOutPort::add)
            .done();
    }
}