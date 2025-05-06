package lkdcode.wanted.ecommerce.modules.products.domain.model.create;

import lkdcode.wanted.ecommerce.modules.products.domain.entity.ProductOptionId;
import lkdcode.wanted.ecommerce.modules.products.domain.value.image.ProductAltText;
import lkdcode.wanted.ecommerce.modules.products.domain.value.image.ProductImageDisplayOrder;
import lkdcode.wanted.ecommerce.modules.products.domain.value.image.ProductImageIsPrimary;
import lkdcode.wanted.ecommerce.modules.products.domain.value.image.ProductImageUrl;

public record ProductImageModel(
    ProductImageUrl url,
    ProductAltText altText,
    ProductImageIsPrimary isPrimary,
    ProductImageDisplayOrder displayOrder,
    ProductOptionId optionId
) {
}
