package com.ecommerce.products.controller.mapper;

import com.ecommerce.products.application.dto.PaginationDto;
import com.ecommerce.products.application.dto.ProductDto;
import com.ecommerce.products.controller.dto.*;
import org.springframework.stereotype.Component;

@Component
public class ProductControllerMapper {
    // Controller DTO → Service DTO 매핑
    public ProductDto.CreateRequest toProductDtoCreateRequest(ProductCreateRequest request) {
        return ProductDto.CreateRequest.builder()
                .name(request.name())
                .slug(request.slug())
                .shortDescription(request.shortDescription())
                .fullDescription(request.fullDescription())
                .sellerId(request.sellerId())
                .brandId(request.brandId())
                .status(request.status())
                .detail(toProductDtoDetail(request.detail()))
                .price(toProductDtoPrice(request.price()))
                .categories(request.categories().stream().map(this::toProductDtoProductCategory).toList())
                .optionGroups(request.optionGroups().stream().map(this::toProductDtoOptionGroup).toList())
                .images(request.images().stream().map(this::toProductDtoImage).toList())
                .tagIds(request.tagIds().stream().toList())
                .build();
    }

    public ProductDto.UpdateRequest toServiceUpdateDto(ProductUpdateRequest request) {
        return ProductDto.UpdateRequest.builder()
                .name(request.name())
                .slug(request.slug())
                .shortDescription(request.shortDescription())
                .fullDescription(request.fullDescription())
                .sellerId(request.sellerId())
                .brandId(request.brandId())
                .status(request.status())
                .detail(toProductDtoDetail(request.detail()))
                .price(toProductDtoPrice(request.price()))
                .categories(request.categories().stream().map(this::toProductDtoProductCategory).toList())
                .tagIds(request.tagIds().stream().toList())
                .build();
    }

    public ProductDto.Detail toProductDtoDetail(ProductCreateRequest.ProductDetailDto detailDto) {
        return ProductDto.Detail.builder()
                .weight(detailDto.weight())
                .dimensions(detailDto.dimensions())
                .materials(detailDto.materials())
                .countryOfOrigin(detailDto.countryOfOrigin())
                .warrantyInfo(detailDto.warrantyInfo())
                .careInstructions(detailDto.careInstructions())
                .additionalInfo(detailDto.additionalInfo())
                .build();
    }

    public ProductDto.Price toProductDtoPrice(ProductCreateRequest.ProductPriceDto priceDto) {
        return ProductDto.Price.builder()
                .basePrice(priceDto.basePrice())
                .salePrice(priceDto.salePrice())
                .costPrice(priceDto.costPrice())
                .currency(priceDto.currency())
                .taxRate(priceDto.taxRate())
                .build();
    }

    public ProductDto.ProductCategory toProductDtoProductCategory(ProductCreateRequest.ProductCategoryDto categoryDto) {
        return ProductDto.ProductCategory.builder()
                .categoryId(categoryDto.categoryId())
                .isPrimary(categoryDto.isPrimary())
                .build();
    }

    public ProductDto.OptionGroup toProductDtoOptionGroup(ProductCreateRequest.OptionGroupDto optionGroupDto) {
        return ProductDto.OptionGroup.builder()
                .name(optionGroupDto.name())
                .options(optionGroupDto.options().stream().map(this::toProductDtoOption).toList())
                .displayOrder(optionGroupDto.displayOrder())
                .build();
    }

    public ProductDto.Option toProductDtoOption(ProductCreateRequest.OptionDto optionDto) {
        return ProductDto.Option.builder()
                .name(optionDto.name())
                .additionalPrice(optionDto.additionalPrice())
                .sku(optionDto.sku())
                .stock(optionDto.stock())
                .displayOrder(optionDto.displayOrder())
                .build();
    }

    public ProductDto.Image toProductDtoImage(ProductCreateRequest.ImageDto imageDto) {
        return ProductDto.Image.builder()
                .url(imageDto.url())
                .altText(imageDto.altText())
                .isPrimary(imageDto.isPrimary())
                .displayOrder(imageDto.displayOrder())
                .build();
    }

    public ProductDto.Option toProductDtoOptionOptionId(Long optionId, ProductOptionRequest request) {
        var option = toProductDtoOption(request);
        option.setId(optionId);
        return option;
    }

    public ProductDto.Option toProductDtoOptionWithOptionGroupId(Long optionGroupId, ProductOptionRequest request) {
        var option = toProductDtoOption(request);
        option.setOptionGroupId(optionGroupId);
        return option;
    }

    public ProductDto.Option toProductDtoOption(ProductOptionRequest request) {
        return ProductDto.Option.builder()
                .name(request.name())
                .additionalPrice(request.additionalPrice())
                .sku(request.sku())
                .stock(request.stock())
                .displayOrder(request.displayOrder())
                .build();
    }

    public ProductDto.Image toProductDtoImage(ProductImageRequest request) {
        return ProductDto.Image.builder()
                .url(request.url())
                .altText(request.altText())
                .isPrimary(request.isPrimary())
                .displayOrder(request.displayOrder())
                .build();
    }

    public ProductDto.ListRequest toProductDtoListRequest(ProductListRequest request) {
        return ProductDto.ListRequest.builder()
                .status(request.status())
                .minPrice(request.minPrice())
                .maxPrice(request.maxPrice())
                .category(request.category())
                .seller(request.seller())
                .brand(request.brand())
                .inStock(request.inStock())
                .tag(request.tag())
                .search(request.search())
                .createdFrom(request.createdFrom())
                .createdTo(request.createdTo())
                .pagination(toPaginationInfo(request))
                .build();
    }

    public PaginationDto.PaginationRequest toPaginationInfo(ProductListRequest request) {
        return PaginationDto.PaginationRequest.builder()
                .page(request.page())
                .size(request.perPage())
                .sort(request.sort())
                .build();
    }
}
