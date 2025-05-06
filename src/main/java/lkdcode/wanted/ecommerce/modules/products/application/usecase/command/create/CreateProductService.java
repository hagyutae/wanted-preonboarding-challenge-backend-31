package lkdcode.wanted.ecommerce.modules.products.application.usecase.command.create;

import jakarta.transaction.Transactional;
import lkdcode.wanted.ecommerce.modules.products.application.ports.out.command.CreateProductOutPort;
import lkdcode.wanted.ecommerce.modules.products.application.ports.out.validator.*;
import lkdcode.wanted.ecommerce.modules.products.application.usecase.command.UpsertResult;
import lkdcode.wanted.ecommerce.modules.products.domain.model.create.CreateProductModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class CreateProductService {
    private final CreateProductOutPort createPort;

    private final ProductValidator productValidator;
    private final BrandValidator brandIdValidator;
    private final SellerValidator sellerIdValidator;
    private final CategoryValidator categoryValidator;
    private final OptionValidator optionValidator;
    private final ImageValidator imageValidator;
    private final TagValidator tagValidator;

    public UpsertResult create(final CreateProductModel model) {
        return CreateProductUsecase.execute(model)
            .validBrandId(brandIdValidator::validId)
            .validSellerId(sellerIdValidator::validId)
            .validUniqueSlug(productValidator::validUniqueSlug)
            .saveProduct(createPort::save)

            .validProductCategoryList(categoryValidator::validList)
            .saveProductCategory(createPort::saveCategory)

            .saveProductDetail(createPort::saveDetail)

            .validProductOptionGroup(optionValidator::validOption)
            .validProductOption(optionValidator::validOption)
            .saveProductOption(createPort::saveOption)

            .validImageDisplayOrder(imageValidator::validImageDisplayOrder)

            .saveImage(createPort::saveImage)

            .saveProductPrice(createPort::savePrice)

            .validProductTag(tagValidator::validList)
            .saveProductTag(createPort::saveTag)

            .done();
    }
}