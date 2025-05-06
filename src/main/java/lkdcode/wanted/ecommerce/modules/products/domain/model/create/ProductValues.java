package lkdcode.wanted.ecommerce.modules.products.domain.model.create;

import lkdcode.wanted.ecommerce.modules.products.domain.entity.ProductBrandId;
import lkdcode.wanted.ecommerce.modules.products.domain.entity.ProductSellerId;
import lkdcode.wanted.ecommerce.modules.products.domain.value.*;

import java.util.Objects;

public record ProductValues(
    ProductName name,
    ProductSlug slug,
    ProductShortDescription shortDescription,
    ProductFullDescription fullDescription,

    ProductSellerId sellerId,
    ProductBrandId brandId,

    ProductStatus status
) {
    public ProductValues(ProductName name, ProductSlug slug, ProductShortDescription shortDescription, ProductFullDescription fullDescription, ProductSellerId sellerId, ProductBrandId brandId, ProductStatus status) {
        this.name = Objects.requireNonNull(name);
        this.slug = Objects.requireNonNull(slug);
        this.shortDescription = Objects.requireNonNull(shortDescription);
        this.fullDescription = Objects.requireNonNull(fullDescription);
        this.sellerId = Objects.requireNonNull(sellerId);
        this.brandId = Objects.requireNonNull(brandId);
        this.status = Objects.requireNonNull(status);
    }
}