package lkdcode.wanted.ecommerce.modules.products.adapter.in.rest.command.dto.request.create;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lkdcode.wanted.ecommerce.modules.products.domain.entity.ProductBrandId;
import lkdcode.wanted.ecommerce.modules.products.domain.entity.ProductSellerId;
import lkdcode.wanted.ecommerce.modules.products.domain.model.create.*;
import lkdcode.wanted.ecommerce.modules.products.domain.value.*;
import lkdcode.wanted.ecommerce.modules.products.domain.value.tag.ProductTag;
import lkdcode.wanted.ecommerce.modules.products.domain.value.tag.ProductTagList;

import java.util.List;

public record UpsertProductDTO(
    @NotNull(message = "상품의 이름은 필수입니다.")
    @Size(max = ProductName.LENGTH, message = ProductName.LENGTH_ERROR_MESSAGE)
    String name,

    @NotNull(message = "상품의 슬러그는 필수입니다.")
    @Size(max = ProductSlug.LENGTH, message = ProductSlug.LENGTH_ERROR_MESSAGE)
    String slug,

    @Size(max = ProductShortDescription.LENGTH, message = ProductShortDescription.LENGTH_ERROR_MESSAGE)
    String shortDescription,

    String fullDescription,

    @Positive
    Long sellerId,

    @Positive
    Long brandId,

    ProductStatus status,

    @Valid
    Detail detail,

    @Valid
    Price price,

    List<@Valid CategoryMapping> categories,

    List<@Valid OptionGroup> optionGroups,

    List<@Valid Image> images,

    List<@Positive Long> tags
) {

    public ProductValues getProductValues() {
        return new ProductValues(
            new ProductName(name),
            new ProductSlug(slug),
            new ProductShortDescription(shortDescription),
            new ProductFullDescription(fullDescription),
            new ProductSellerId(sellerId),
            new ProductBrandId(brandId),
            status
        );
    }

    public ProductCategoryList getProductCategoryList() {
        return new ProductCategoryList(
            categories.stream()
                .map(dto -> new ProductCategoryModel(
                    dto.getProductCategoryId(),
                    dto.getProductCategoryIsPrimary()
                ))
                .toList()
        );
    }

    public ProductImageList getProductImageList() {
        return new ProductImageList(
            images.stream()
                .map(Image::getProductImageModel)
                .toList()
        );
    }

    public ProductDetailModel getProductDetailModel() {
        return detail.getProductDetailModel();
    }

    public ProductPriceModel getProductPriceModel() {
        return price.getProductPriceModel();
    }

    public ProductOptionGroupList getProductOptionGroupList() {
        return new ProductOptionGroupList(
            optionGroups.stream()
                .map(OptionGroup::getProductOptionGroupModel)
                .toList()
        );
    }

    public ProductTagList getProductTagList() {
        return new ProductTagList(
            tags.stream()
                .map(ProductTag::new)
                .toList()
        );
    }
}