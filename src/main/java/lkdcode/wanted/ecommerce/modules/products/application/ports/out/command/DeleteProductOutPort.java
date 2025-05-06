package lkdcode.wanted.ecommerce.modules.products.application.ports.out.command;

import lkdcode.wanted.ecommerce.modules.products.domain.entity.ProductId;

public interface DeleteProductOutPort {
    void delete(ProductId id);
}