package com.pawn.wantedcqrs.product.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pawn.wantedcqrs.product.entity.Product;
import com.pawn.wantedcqrs.product.entity.ProductDetail;
import com.pawn.wantedcqrs.product.entity.ProductPrice;
import com.pawn.wantedcqrs.product.entity.ProductStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ProductDto {

    private Long id;

    private String name;

    private String slug;

    private String shortDescription;

    private String fullDescription;

    private Long sellerId;

    private Long brandId;

    private ProductStatus status;

    private ProductDetailDto detail;

    private ProductPriceDto price;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime updatedAt;


//    private List<CategoryDto> categories;
//
//    private List<OptionGroupDto> optionGroups;
//
//    private List<ProductImageDto> images;
//
//    private List<TagDto> tags;

    public Product toEntity(ProductDetailDto detail, ProductPriceDto price) {
        return Product.builder()
                .id(id)
                .name(name)
                .slug(slug)
                .shortDescription(shortDescription)
                .fullDescription(fullDescription)
                .sellerId(sellerId)
                .brandId(brandId)
                .status(status)
                .productDetail(detail.toEntity())
                .productPrice(price.toEntity())
                .build();
    }

    public static ProductDto fromEntity(Product product) {
        if (product == null) return null;

        ProductDetail detail = product.getProductDetail();
        ProductDetailDto detailDto = new ProductDetailDto();
        detailDto.setId(detail.getId());
        detailDto.setWeight(detail.getWeight());
        detailDto.setMaterials(detail.getMaterials());
        detailDto.setCountryOfOrigin(detail.getCountryOfOrigin());
        detailDto.setWarrantyInfo(detail.getWarrantyInfo());
        detailDto.setCareInstructions(detail.getCareInstructions());
        detailDto.setDimensions(new ProductDetailDto.Dimensions(
                detail.getDimensions().getWidth(),
                detail.getDimensions().getHeight(),
                detail.getDimensions().getDepth()
        ));
        detailDto.setAdditionalInfo(new ProductDetailDto.AdditionalInfo(
                detail.getAdditionalInfo().isAssemblyRequired(),
                detail.getAdditionalInfo().getAssemblyTime()
        ));

        ProductPrice price = product.getProductPrice();
        ProductPriceDto priceDto = new ProductPriceDto();
        priceDto.setId(price.getId());
        priceDto.setBasePrice(price.getBasePrice());
        priceDto.setSalePrice(price.getSalePrice());
        priceDto.setCostPrice(price.getCostPrice());
        priceDto.setCurrency(price.getCurrency());
        priceDto.setTaxRate(price.getTaxRate());

        ProductDto dto = new ProductDto();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setSlug(product.getSlug());
        dto.setShortDescription(product.getShortDescription());
        dto.setFullDescription(product.getFullDescription());
        dto.setSellerId(product.getSellerId());
        dto.setBrandId(product.getBrandId());
        dto.setStatus(product.getStatus());
        dto.setDetail(detailDto);
        dto.setPrice(priceDto);
        dto.setCreatedAt(product.getCreatedAt());
        dto.setUpdatedAt(product.getUpdatedAt());

        return dto;
    }

}
