package com.example.cqrsapp.product.service;

import com.example.cqrsapp.product.domain.*;
import com.example.cqrsapp.product.dto.requset.RegisterProductDto;
import com.example.cqrsapp.seller.domain.Seller;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

import static com.example.cqrsapp.product.dto.requset.RegisterProductDto.*;

@Component
@RequiredArgsConstructor
public class ProductMapper {

    public Product mapProductFromDto(RegisterProductDto dto, Seller seller, Brand brand) {
        return Product.builder()
                .name(dto.getName())
                .slug(dto.getSlug())
                .shortDescription(dto.getShortDescription())
                .fullDescription(dto.getFullDescription())
                .seller(seller)
                .brand(brand)
                .status(dto.getStatus())
                .productDetail(toProductDetail(dto.getDetail()))
                .productPrice(toProductPrice(dto.getPrice()))
                .productImages(dto.getImages().stream().map(this::toProductImage).toList())
                .productOptionGroups(dto.getOptionGroups().stream().map(this::toProductOptionGroup).toList())
                .build();
    }

   public ProductDetail toProductDetail(DetailDto dto) {
        return ProductDetail.builder()
                .weight(BigDecimal.valueOf(dto.getWeight()))
                .dimensions(toDimensions(dto.getDimensions()))
                .materials(dto.getMaterials())
                .countryOfOrigin(dto.getCountryOfOrigin())
                .warrantyInfo(dto.getWarrantyInfo())
                .careInstructions(dto.getCareInstructions())
                .additionalInfo(dto.getAdditionalInfo())
                .build();
    }

    public ProductPrice toProductPrice(PriceDto priceDto) {
        return ProductPrice.builder()
                .basePrice(BigDecimal.valueOf(priceDto.getBasePrice()))
                .salePrice(BigDecimal.valueOf(priceDto.getSalePrice()))
                .costPrice(BigDecimal.valueOf(priceDto.getCostPrice()))
                .currency(priceDto.getCurrency())
                .taxRate(BigDecimal.valueOf(priceDto.getTaxRate()))
                .build();
    }

    public ProductImage toProductImage(ImageDto images) {
        return ProductImage.builder()
                .url(images.getUrl())
                .altText(images.getAltText())
                .isPrimary(images.getIsPrimary())
                .displayOrder(images.getDisplayOrder())
                .optionId(images.getOptionId())
                .build();
    }

    public ProductOptionGroup toProductOptionGroup(OptionGroupDto optionGroupDto) {
        return ProductOptionGroup.builder()
                .name(optionGroupDto.getName())
                .displayOrder(optionGroupDto.getDisplayOrder())
                .productOptions(optionGroupDto.getOptions().stream().map(this::toProductOption).toList())
                .build();
    }

    public ProductOption toProductOption(OptionDto optionDto) {
        return ProductOption.builder()
                .name(optionDto.getName())
                .additionalPrice(BigDecimal.valueOf(optionDto.getAdditionalPrice()))
                .sku(optionDto.getSku())
                .stock(optionDto.getStock())
                .displayOrder(optionDto.getDisplayOrder())
                .build();
    }

    private Dimensions toDimensions(DimensionsDto dto) {
        return Dimensions.builder()
                .depth(dto.getDepth())
                .width(dto.getWidth())
                .height(dto.getHeight())
                .build();
    }
}
