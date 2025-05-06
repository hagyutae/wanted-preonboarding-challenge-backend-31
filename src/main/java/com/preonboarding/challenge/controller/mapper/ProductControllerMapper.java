package com.preonboarding.challenge.controller.mapper;

import com.preonboarding.challenge.controller.dto.*;
import com.preonboarding.challenge.service.dto.PaginationDto;
import com.preonboarding.challenge.service.product.ProductCommand;
import com.preonboarding.challenge.service.product.ProductDto;
import com.preonboarding.challenge.service.query.ProductQuery;
import org.springframework.stereotype.Component;

@Component
public class ProductControllerMapper {
    // Controller DTO → Service DTO 매핑
    public ProductCommand.CreateProduct toCreateProductCommand(ProductCreateRequest request) {
        return ProductCommand.CreateProduct.builder()
                .name(request.getName())
                .slug(request.getSlug())
                .shortDescription(request.getShortDescription())
                .fullDescription(request.getFullDescription())
                .sellerId(request.getSellerId())
                .brandId(request.getBrandId())
                .status(request.getStatus())
                .detail(toProductDtoDetail(request.getDetail()))
                .price(toProductDtoPrice(request.getPrice()))
                .categories(request.getCategories().stream().map(this::toProductDtoProductCategory).toList())
                .optionGroups(request.getOptionGroups().stream().map(this::toProductDtoOptionGroup).toList())
                .images(request.getImages().stream().map(this::toProductDtoImage).toList())
                .tagIds(request.getTagIds().stream().toList())
                .build();
    }

    public ProductCommand.UpdateProduct toUpdateProductCommand(Long productId, ProductUpdateRequest request) {
        return ProductCommand.UpdateProduct.builder()
                .productId(productId)
                .name(request.getName())
                .slug(request.getSlug())
                .shortDescription(request.getShortDescription())
                .fullDescription(request.getFullDescription())
                .sellerId(request.getSellerId())
                .brandId(request.getBrandId())
                .status(request.getStatus())
                .detail(toProductDtoDetail(request.getDetail()))
                .price(toProductDtoPrice(request.getPrice()))
                .categories(request.getCategories().stream().map(this::toProductDtoProductCategory).toList())
                .tagIds(request.getTagIds().stream().toList())
                .build();
    }

    public ProductDto.Detail toProductDtoDetail(ProductCreateRequest.ProductDetailDto detailDto) {
        return ProductDto.Detail.builder()
                .weight(detailDto.getWeight())
                .dimensions(detailDto.getDimensions())
                .materials(detailDto.getMaterials())
                .countryOfOrigin(detailDto.getCountryOfOrigin())
                .warrantyInfo(detailDto.getWarrantyInfo())
                .careInstructions(detailDto.getCareInstructions())
                .additionalInfo(detailDto.getAdditionalInfo())
                .build();
    }

    public ProductDto.Price toProductDtoPrice(ProductCreateRequest.ProductPriceDto priceDto) {
        return ProductDto.Price.builder()
                .basePrice(priceDto.getBasePrice())
                .salePrice(priceDto.getSalePrice())
                .costPrice(priceDto.getCostPrice())
                .currency(priceDto.getCurrency())
                .taxRate(priceDto.getTaxRate())
                .build();
    }

    public ProductDto.ProductCategory toProductDtoProductCategory(ProductCreateRequest.ProductCategoryDto categoryDto) {
        return ProductDto.ProductCategory.builder()
                .categoryId(categoryDto.getCategoryId())
                .isPrimary(categoryDto.getIsPrimary())
                .build();
    }

    public ProductDto.OptionGroup toProductDtoOptionGroup(ProductCreateRequest.OptionGroupDto optionGroupDto) {
        return ProductDto.OptionGroup.builder()
                .name(optionGroupDto.getName())
                .options(optionGroupDto.getOptions().stream().map(this::toProductDtoOption).toList())
                .displayOrder(optionGroupDto.getDisplayOrder())
                .build();
    }

    public ProductDto.Option toProductDtoOption(ProductCreateRequest.OptionDto optionDto) {
        return ProductDto.Option.builder()
                .name(optionDto.getName())
                .additionalPrice(optionDto.getAdditionalPrice())
                .sku(optionDto.getSku())
                .stock(optionDto.getStock())
                .displayOrder(optionDto.getDisplayOrder())
                .build();
    }

    public ProductDto.Image toProductDtoImage(ProductCreateRequest.ImageDto imageDto) {
        return ProductDto.Image.builder()
                .url(imageDto.getUrl())
                .altText(imageDto.getAltText())
                .isPrimary(imageDto.isPrimary())
                .displayOrder(imageDto.getDisplayOrder())
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
                .name(request.getName())
                .additionalPrice(request.getAdditionalPrice())
                .sku(request.getSku())
                .stock(request.getStock())
                .displayOrder(request.getDisplayOrder())
                .build();
    }

    public ProductDto.Image toProductDtoImage(ProductImageRequest request) {
        return ProductDto.Image.builder()
                .url(request.getUrl())
                .altText(request.getAltText())
                .isPrimary(request.isPrimary())
                .displayOrder(request.getDisplayOrder())
                .build();
    }

    public ProductQuery.ListProducts toProductDtoListRequest(ProductListRequest request) {
        return ProductQuery.ListProducts.builder()
                .status(request.getStatus())
                .minPrice(request.getMinPrice())
                .maxPrice(request.getMaxPrice())
                .category(request.getCategory())
                .seller(request.getSeller())
                .brand(request.getBrand())
                .inStock(request.getInStock())
                .tag(request.getTag())
                .search(request.getSearch())
                .createdFrom(request.getCreatedFrom())
                .createdTo(request.getCreatedTo())
                .pagination(toPaginationInfo(request))
                .build();
    }

    public PaginationDto.PaginationRequest toPaginationInfo(ProductListRequest request) {
        return PaginationDto.PaginationRequest.builder()
                .page(request.getPage())
                .size(request.getPerPage())
                .sort(request.getSort())
                .build();
    }
}
