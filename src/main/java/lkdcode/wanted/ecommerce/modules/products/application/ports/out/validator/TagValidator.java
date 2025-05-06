package lkdcode.wanted.ecommerce.modules.products.application.ports.out.validator;

import lkdcode.wanted.ecommerce.modules.products.domain.value.tag.ProductTagList;

public interface TagValidator {
    void validList(ProductTagList list);
}
