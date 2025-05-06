package kr.co.wanted.backend31.controller.vo;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import kr.co.wanted.backend31.common.model.product.specification.ProductCreateSpecification;
import kr.co.wanted.backend31.common.model.product.specification.ProductTagCreateSpecification;

public record ProductCreateRequest(@NotBlank String name, @NotBlank String slug, @NotBlank String shortDescription,
        @NotBlank String fullDescription,
        long sellerId, long brandId, @NotBlank String status, @NotNull ProductDetailCreateRequest detail,
        @NotNull ProductPriceCreateRequest price,
        @NotNull @Valid List<@NotNull ProductCategoryCreateRequest> categories,
        @NotNull @Valid List<@NotNull ProductOptionGroupCreateRequest> optionGroups,
        @NotNull @Valid List<@NotNull ProductImageCreateRequest> images, @NotNull List<@NotNull Long> tags) {

    public ProductCreateSpecification to() {
        return ProductCreateSpecification.builder()
                .name(name)
                .slug(slug)
                .shortDescription(shortDescription)
                .fullDescription(fullDescription)
                .sellerId(sellerId)
                .brandId(brandId)
                .status(status)
                .detailSpec(detail.to())
                .priceSpec(price.to())
                .categorySpecs(categories.stream().map(ProductCategoryCreateRequest::to).toList())
                .optionGroupSpecs(optionGroups.stream().map(ProductOptionGroupCreateRequest::to).toList())
                .imageSpecs(images.stream().map(ProductImageCreateRequest::to).toList())
                .tagSpecs(tags.stream().map(ProductTagCreateSpecification::new).toList())
                .build();
    }
}
