package lkdcode.wanted.ecommerce.modules.products.adapter.in.rest.command.dto.request.create;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lkdcode.wanted.ecommerce.modules.products.domain.entity.ProductOptionId;
import lkdcode.wanted.ecommerce.modules.products.domain.model.create.ProductImageModel;
import lkdcode.wanted.ecommerce.modules.products.domain.value.image.ProductAltText;
import lkdcode.wanted.ecommerce.modules.products.domain.value.image.ProductImageDisplayOrder;
import lkdcode.wanted.ecommerce.modules.products.domain.value.image.ProductImageIsPrimary;
import lkdcode.wanted.ecommerce.modules.products.domain.value.image.ProductImageUrl;

public record Image(
    @NotNull()
    @Size(max = ProductImageUrl.LENGTH)
    String url,

    @Size(max = ProductAltText.LENGTH)
    String altText,

    Boolean isPrimary,

    Integer displayOrder,

    Long optionId
) {

    public ProductImageModel getProductImageModel() {
        return new ProductImageModel(
            new ProductImageUrl(url),
            new ProductAltText(altText),
            new ProductImageIsPrimary(isPrimary),
            new ProductImageDisplayOrder(displayOrder),
            optionId == null ? null : new ProductOptionId(optionId)
        );
    }
}