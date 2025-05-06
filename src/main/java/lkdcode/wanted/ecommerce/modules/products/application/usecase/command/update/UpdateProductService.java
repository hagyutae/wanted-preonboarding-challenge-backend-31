package lkdcode.wanted.ecommerce.modules.products.application.usecase.command.update;

import jakarta.transaction.Transactional;
import lkdcode.wanted.ecommerce.modules.products.application.ports.out.command.UpdateProductOutPort;
import lkdcode.wanted.ecommerce.modules.products.application.ports.out.validator.*;
import lkdcode.wanted.ecommerce.modules.products.application.usecase.command.UpsertResult;
import lkdcode.wanted.ecommerce.modules.products.domain.entity.ProductId;
import lkdcode.wanted.ecommerce.modules.products.domain.model.update.UpdateProductModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class UpdateProductService {
    private final UpdateProductOutPort updatePort;

    private final ProductValidator productValidator;
    private final BrandValidator brandIdValidator;
    private final SellerValidator sellerIdValidator;
    private final CategoryValidator categoryValidator;
    private final OptionValidator optionValidator;
    private final ImageValidator imageValidator;
    private final TagValidator tagValidator;

    public UpsertResult update(final ProductId productId, final UpdateProductModel model) {
        return UpdateProductUsecase.execute(productId, model)
            .validBrandId(brandIdValidator::validId)
            .validAuthoritySeller(sellerIdValidator::validAuthoritySeller)
            .validUniqueSlug(productValidator::validUniqueSlugForUpdate)
            .updateProduct(updatePort::update)

            .validProductCategoryList(categoryValidator::validList)
            .updateProductCategory(updatePort::updateCategory)

            .updateProductDetail(updatePort::updateDetail)

            .validProductOptionGroup(optionValidator::validOption)
            .validProductOption(optionValidator::validOption)
            .updateProductOption(updatePort::updateOption)

            .validImageDisplayOrder(imageValidator::validImageDisplayOrder)

            .updateImage(updatePort::updateImage)

            .updateProductPrice(updatePort::updatePrice)

            .validProductTag(tagValidator::validList)
            .updateProductTag(updatePort::updateTag)

            .done();
    }
}