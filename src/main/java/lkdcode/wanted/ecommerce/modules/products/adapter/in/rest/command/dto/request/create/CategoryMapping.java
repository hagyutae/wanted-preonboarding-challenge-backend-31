package lkdcode.wanted.ecommerce.modules.products.adapter.in.rest.command.dto.request.create;

import lkdcode.wanted.ecommerce.modules.products.domain.entity.ProductCategoryId;
import lkdcode.wanted.ecommerce.modules.products.domain.value.category.ProductCategoryIsPrimary;

public record CategoryMapping(
    Long categoryId,
    Boolean isPrimary
) {

    public ProductCategoryId getProductCategoryId() {
        return new ProductCategoryId(categoryId);
    }

    public ProductCategoryIsPrimary getProductCategoryIsPrimary() {
        return new ProductCategoryIsPrimary(isPrimary);
    }
}
