package lkdcode.wanted.ecommerce.modules.products.application.usecase.query;

import lkdcode.wanted.ecommerce.modules.products.domain.entity.ProductId;

import java.util.function.Consumer;
import java.util.function.Function;

class QueryProductDetailUsecase {
    private final ProductId id;

    private QueryProductDetailUsecase(ProductId id) {
        this.id = id;
    }

    public static QueryProductDetailUsecase execute(ProductId id) {
        return new QueryProductDetailUsecase(id);
    }

    public QueryProductDetailUsecase validProductId(final Consumer<ProductId> valid) {
        valid.accept(id);
        return this;
    }

    public <T> T read(final Function<ProductId, T> read) {
        return read.apply(id);
    }
}