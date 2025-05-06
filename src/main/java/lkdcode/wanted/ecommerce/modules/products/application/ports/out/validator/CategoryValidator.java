package lkdcode.wanted.ecommerce.modules.products.application.ports.out.validator;

import lkdcode.wanted.ecommerce.modules.products.domain.model.create.ProductCategoryList;

public interface CategoryValidator {
    void validList(ProductCategoryList target);
}
