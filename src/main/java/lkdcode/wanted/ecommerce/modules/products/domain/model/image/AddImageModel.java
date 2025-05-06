package lkdcode.wanted.ecommerce.modules.products.domain.model.image;

import lkdcode.wanted.ecommerce.modules.products.domain.entity.ProductId;
import lkdcode.wanted.ecommerce.modules.products.domain.entity.ProductOptionId;
import lkdcode.wanted.ecommerce.modules.products.domain.value.image.ProductAltText;
import lkdcode.wanted.ecommerce.modules.products.domain.value.image.ProductImageDisplayOrder;
import lkdcode.wanted.ecommerce.modules.products.domain.value.image.ProductImageIsPrimary;
import lkdcode.wanted.ecommerce.modules.products.domain.value.image.ProductImageUrl;

public record AddImageModel(
    ProductId productId,
    ProductImageUrl url,
    ProductAltText altText,
    ProductImageIsPrimary isPrimary,
    ProductImageDisplayOrder displayOrder,
    ProductOptionId optionId
) {
}