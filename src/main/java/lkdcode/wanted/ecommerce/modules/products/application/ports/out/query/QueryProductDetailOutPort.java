package lkdcode.wanted.ecommerce.modules.products.application.ports.out.query;

import lkdcode.wanted.ecommerce.modules.products.application.usecase.query.dto.detail.QueryProductDetailResult;
import lkdcode.wanted.ecommerce.modules.products.domain.entity.ProductId;

public interface QueryProductDetailOutPort {
    QueryProductDetailResult load(ProductId id);
}
