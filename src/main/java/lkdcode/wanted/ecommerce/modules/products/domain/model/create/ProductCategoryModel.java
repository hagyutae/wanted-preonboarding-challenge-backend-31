package lkdcode.wanted.ecommerce.modules.products.domain.model.create;

import lkdcode.wanted.ecommerce.modules.products.domain.entity.ProductCategoryId;
import lkdcode.wanted.ecommerce.modules.products.domain.value.category.ProductCategoryIsPrimary;

import java.util.Objects;

public record ProductCategoryModel(
    ProductCategoryId id,
    ProductCategoryIsPrimary isPrimary
) {
    public ProductCategoryModel(ProductCategoryId id, ProductCategoryIsPrimary isPrimary) {
        this.id = Objects.requireNonNull(id);
        this.isPrimary = Objects.requireNonNull(isPrimary);
    }
}
