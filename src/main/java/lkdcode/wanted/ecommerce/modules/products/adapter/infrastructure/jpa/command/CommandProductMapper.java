package lkdcode.wanted.ecommerce.modules.products.adapter.infrastructure.jpa.command;

import lkdcode.wanted.ecommerce.modules.brand.adapter.infrastructure.jpa.entity.BrandJpaEntity;
import lkdcode.wanted.ecommerce.modules.category.adapter.infrastructure.jpa.entity.CategoryJpaEntity;
import lkdcode.wanted.ecommerce.modules.products.adapter.infrastructure.jpa.entity.*;
import lkdcode.wanted.ecommerce.modules.products.application.usecase.command.UpsertResult;
import lkdcode.wanted.ecommerce.modules.products.domain.entity.ProductId;
import lkdcode.wanted.ecommerce.modules.products.domain.model.create.*;
import lkdcode.wanted.ecommerce.modules.products.domain.value.ProductName;
import lkdcode.wanted.ecommerce.modules.products.domain.value.ProductSlug;
import lkdcode.wanted.ecommerce.modules.sellers.adapter.infrastructure.jpa.entity.SellerJpaEntity;
import lkdcode.wanted.ecommerce.modules.tag.adapter.infrastructure.jpa.entity.TagJpaEntity;
import org.springframework.stereotype.Service;

@Service
class CommandProductMapper {

    public ProductJpaEntity convert(final ProductValues target, final SellerJpaEntity seller, final BrandJpaEntity brand) {
        return ProductJpaEntity.builder()
            .name(target.name().value())
            .slug(target.slug().value())
            .shortDescription(target.shortDescription().value())
            .fullDescription(target.fullDescription().value())
            .seller(seller)
            .brand(brand)
            .status(target.status())
            .build();
    }

    public ProductDetailJpaEntity convert(final ProductJpaEntity product, final ProductDetailModel model) {
        return ProductDetailJpaEntity.builder()
            .product(product)
            .weight(model.weight().value())
            .dimensions(model.dimension().value())
            .materials(model.material().value())
            .countryOfOrigin(model.countryOfOrigin().value())
            .warrantyInfo(model.warrantyInfo().value())
            .careInstructions(model.careInstructions().value())
            .additionalInfo(model.additionalInfo().value())
            .build();
    }

    public ProductPriceJpaEntity convert(final ProductJpaEntity product, final ProductPriceModel model) {
        return ProductPriceJpaEntity.builder()
            .product(product)
            .basePrice(model.basePrice().value())
            .salePrice(model.salePrice().value())
            .costPrice(model.costPrice().value())
            .currency(model.currency().value())
            .taxRate(model.taxRate().value())
            .build();
    }

    public ProductTagJpaEntity convert(final ProductJpaEntity product, final TagJpaEntity tagEntity) {
        return ProductTagJpaEntity.builder()
            .product(product)
            .tag(tagEntity)
            .build();
    }

    public ProductImageJpaEntity convert(final ProductJpaEntity product, final ProductImageModel model, final ProductOptionJpaEntity option) {
        return ProductImageJpaEntity.builder()
            .product(product)
            .url(model.url().value())
            .altText(model.altText().value())
            .isPrimary(model.isPrimary().value())
            .displayOrder(model.displayOrder().value())
            .option(option)
            .build();
    }

    public ProductCategoryJpaEntity convert(final ProductJpaEntity product, final ProductCategoryModel model, final CategoryJpaEntity categoryEntity) {
        return ProductCategoryJpaEntity.builder()
            .product(product)
            .category(categoryEntity)
            .isPrimary(model.isPrimary().value())
            .build();
    }

    public ProductOptionGroupJpaEntity convert(final ProductJpaEntity product, final ProductOptionGroupModel model) {
        return ProductOptionGroupJpaEntity.builder()
            .product(product)
            .name(model.groupName().value())
            .displayOrder(model.groupDisplayOrder().value())
            .build();
    }

    public ProductOptionJpaEntity convert(final ProductOptionGroupJpaEntity optionGroup, final ProductOptionModel model) {
        return ProductOptionJpaEntity.builder()
            .optionGroup(optionGroup)
            .name(model.optionName().value())
            .additionalPrice(model.additionalPrice().value())
            .sku(model.sku().value())
            .stock(model.stock().value())
            .displayOrder(model.displayOrder().value())
            .build();
    }

    public UpsertResult convertUpsertResult(final ProductJpaEntity saved) {
        return UpsertResult.builder()
            .id(new ProductId(saved.getId()))
            .name(new ProductName(saved.getName()))
            .slug(new ProductSlug(saved.getSlug()))
            .createdAt(saved.getCreatedAt())
            .updatedAt(saved.getUpdatedAt())
            .build();
    }
}
