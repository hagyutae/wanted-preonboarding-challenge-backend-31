package lkdcode.wanted.ecommerce.modules.products.application.ports.out.option.command;

import lkdcode.wanted.ecommerce.modules.products.domain.entity.ProductOptionId;

public interface DeleteOptionOutPort {
    void delete(ProductOptionId target);
}
