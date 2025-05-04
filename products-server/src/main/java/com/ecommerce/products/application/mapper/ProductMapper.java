package com.ecommerce.products.application.mapper;

import com.ecommerce.products.application.dto.ProductDto;
import com.ecommerce.products.infra.jpa.entity.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ProductMapper {

    private final ObjectMapper om;

    public Product toProductEntity(ProductDto.CreateRequest request) {
        return Product.builder()
                .name(request.getName())
                .slug(request.getSlug())
                .shortDescription(request.getShortDescription())
                .fullDescription(request.getFullDescription())
                .status(ProductStatus.valueOf(request.getStatus()))
                .build();
    }

    public ProductDetail toProductDetailEntity(ProductDto.Detail detail, Product product) {
        return ProductDetail.builder()
                .product(product)
                .weight(detail.getWeight())
                .dimensions(convertMapToJsonString(detail.getDimensions()))
                .materials(detail.getMaterials())
                .countryOfOrigin(detail.getCountryOfOrigin())
                .warrantyInfo(detail.getWarrantyInfo())
                .careInstructions(detail.getCareInstructions())
                .additionalInfo(convertMapToJsonString(detail.getAdditionalInfo()))
                .build();
    }

    public ProductPrice toProductPriceEntity(ProductDto.Price price, Product product) {
        return ProductPrice.builder()
                .product(product)
                .basePrice(price.getBasePrice())
                .salePrice(price.getSalePrice())
                .currency(price.getCurrency())
                .taxRate(price.getTaxRate())
                .build();
    }

    public ProductOptionGroup toProductOptionGroupEntity(ProductDto.OptionGroup group, Product product) {
        return ProductOptionGroup.builder()
                .product(product)
                .name(group.getName())
                .displayOrder(group.getDisplayOrder())
                .build();
    }

    public ProductOption toProductOptionEntity(ProductDto.Option option, ProductOptionGroup optionGroup) {
        return ProductOption.builder()
                .optionGroup(optionGroup)
                .name(option.getName())
                .additionalPrice(option.getAdditionalPrice())
                .sku(option.getSku())
                .stock(option.getStock())
                .displayOrder(option.getDisplayOrder())
                .build();
    }

    public ProductImage toProductImageEntity(ProductDto.Image image, Product product, ProductOption option) {
        return ProductImage.builder()
                .product(product)
                .url(image.getUrl())
                .altText(image.getAltText())
                .isPrimary(image.isPrimary())
                .displayOrder(image.getDisplayOrder())
                .option(option)
                .build();
    }

    public Product updateProductEntity(ProductDto.UpdateRequest request, Product product) {
        if (request.getName() != null) {
            product.setName(request.getName());
        }
        if (request.getSlug() != null) {
            product.setSlug(request.getSlug());
        }
        if (request.getShortDescription() != null) {
            product.setShortDescription(request.getShortDescription());
        }
        if (request.getFullDescription() != null) {
            product.setFullDescription(request.getFullDescription());
        }
        if (request.getStatus() != null) {
            product.setStatus(ProductStatus.valueOf(request.getStatus()));
        }
        return product;
    }

    public ProductDetail updateProductDetailEntity(ProductDto.Detail detail, ProductDetail productDetail) {
        if (detail.getWeight() != null) {
            productDetail.setWeight(detail.getWeight());
        }
        if (detail.getDimensions() != null) {
            productDetail.setDimensions(convertMapToJsonString(detail.getDimensions()));
        }
        if (detail.getMaterials() != null) {
            productDetail.setMaterials(detail.getMaterials());
        }
        if (detail.getCountryOfOrigin() != null) {
            productDetail.setCountryOfOrigin(detail.getCountryOfOrigin());
        }
        if (detail.getWarrantyInfo() != null) {
            productDetail.setWarrantyInfo(detail.getWarrantyInfo());
        }
        if (detail.getCareInstructions() != null) {
            productDetail.setCareInstructions(detail.getCareInstructions());
        }
        if (detail.getAdditionalInfo() != null) {
            productDetail.setAdditionalInfo(convertMapToJsonString(detail.getAdditionalInfo()));
        }
        return productDetail;
    }

    public ProductPrice updateProductPriceEntity(ProductDto.Price price, ProductPrice productPrice) {
        if (price.getBasePrice() != null) {
            productPrice.setBasePrice(price.getBasePrice());
        }
        if (price.getSalePrice() != null) {
            productPrice.setSalePrice(price.getSalePrice());
        }
        if (price.getCurrency() != null) {
            productPrice.setCurrency(price.getCurrency());
        }
        if (price.getTaxRate() != null) {
            productPrice.setTaxRate(price.getTaxRate());
        }
        return productPrice;
    }

    public ProductDto.Product toProductDto(Product product) {
        return ProductDto.Product.builder()
                .id(product.getId())
                .name(product.getName())
                .slug(product.getSlug())
                .shortDescription(product.getShortDescription())
                .fullDescription(product.getFullDescription())
                .seller(toSellerDto(product.getSeller()))
                .brand(toBrandDto(product.getBrand()))
                .status(product.getStatus().name())
                .createdAt(product.getCreatedAt())
                .updatedAt(product.getUpdatedAt())
                .detail(toDetailDto(product.getDetail()))
                .price(toPriceDto(product.getPrice()))
                .categories(product.getCategories().stream()
                        .map(this::toCategoryDto)
                        .collect(Collectors.toList())
                )
                .optionGroups(product.getOptionGroups().stream()
                        .map(this::toOptionGroupDto)
                        .collect(Collectors.toList())
                )
                .images(product.getImages().stream()
                        .map(this::toImageDto)
                        .collect(Collectors.toList())
                )
                .tags(product.getTags().stream()
                        .map(this::toTagDto)
                        .collect(Collectors.toList())
                )
                .build();
    }

    public ProductDto.Brand toBrandDto(Brand brand) {
        return ProductDto.Brand.builder()
                .id(brand.getId())
                .name(brand.getName())
                .build();
    }

    public ProductDto.Seller toSellerDto(Seller seller) {
        return ProductDto.Seller.builder()
                .id(seller.getId())
                .name(seller.getName())
                .build();
    }

    public ProductDto.Detail toDetailDto(ProductDetail detail) {
        return ProductDto.Detail.builder()
                .weight(detail.getWeight())
                .dimensions(convertJsonStringToMap(detail.getDimensions()))
                .materials(detail.getMaterials())
                .countryOfOrigin(detail.getCountryOfOrigin())
                .warrantyInfo(detail.getWarrantyInfo())
                .careInstructions(detail.getCareInstructions())
                .additionalInfo(convertJsonStringToMap(detail.getAdditionalInfo()))
                .build();
    }

    public ProductDto.ProductSummary toProductSummaryDto(Product product) {
        return ProductDto.ProductSummary.builder()
                .id(product.getId())
                .name(product.getName())
                .slug(product.getSlug())
                .shortDescription(product.getShortDescription())
                .basePrice(product.getPrice().getBasePrice())
                .salePrice(product.getPrice().getSalePrice())
                .currency(product.getPrice().getCurrency())
                .primaryImage(product.getImages().stream()
                        .filter(ProductImage::isPrimary)
                        .findFirst()
                        .map(this::toImageDto)
                        .orElse(null)
                )
                .brand(toBrandDto(product.getBrand()))
                .seller(toSellerDto(product.getSeller()))
                .rating(product.getReviews().stream()
                        .mapToInt(Review::getRating)
                        .average()
                        .orElse(0.0)
                )
                .reviewCount(product.getReviews().size())
                .inStock(product.getStatus().equals(ProductStatus.ACTIVE))
                .status(product.getStatus().name())
                .createdAt(product.getCreatedAt())
                .build();
    }

    public ProductDto.Price toPriceDto(ProductPrice price) {
        return ProductDto.Price.builder()
                .basePrice(price.getBasePrice())
                .salePrice(price.getSalePrice())
                .currency(price.getCurrency())
                .taxRate(price.getTaxRate())
                .build();
    }

    public ProductDto.Category toCategoryDto(Category category) {
        return ProductDto.Category.builder()
                .id(category.getId())
                .name(category.getName())
                .slug(category.getSlug())
                .parent(toParentCategoryDto(category.getParent()))
                .build();
    }

    public ProductDto.ParentCategory toParentCategoryDto(Category parent) {
        if (parent == null) {
            return null;
        }
        return ProductDto.ParentCategory.builder()
                .id(parent.getId())
                .name(parent.getName())
                .slug(parent.getSlug())
                .build();
    }

    public ProductDto.OptionGroup toOptionGroupDto(ProductOptionGroup group) {
        return ProductDto.OptionGroup.builder()
                .id(group.getId())
                .name(group.getName())
                .displayOrder(group.getDisplayOrder())
                .options(group.getOptions().stream()
                        .map(this::toOptionDto)
                        .collect(Collectors.toList())
                )
                .build();
    }

    public ProductDto.Option toOptionDto(ProductOption option) {
        return ProductDto.Option.builder()
                .id(option.getId())
                .optionGroupId(option.getOptionGroup().getId())
                .name(option.getName())
                .additionalPrice(option.getAdditionalPrice())
                .sku(option.getSku())
                .stock(option.getStock())
                .displayOrder(option.getDisplayOrder())
                .build();
    }

    public ProductDto.Image toImageDto(ProductImage image) {
        return ProductDto.Image.builder()
                .id(image.getId())
                .url(image.getUrl())
                .altText(image.getAltText())
                .isPrimary(image.isPrimary())
                .displayOrder(image.getDisplayOrder())
                .optionId(image.getOption() != null ? image.getOption().getId() : null)
                .build();
    }

    public ProductDto.Tag toTagDto(Tag tag) {
        return ProductDto.Tag.builder()
                .id(tag.getId())
                .name(tag.getName())
                .slug(tag.getSlug())
                .build();
    }

    private String convertMapToJsonString(Map<String, Object> map) {
        if (map == null) {
            return null;
        }

        try {
            return om.writeValueAsString(map);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error converting Map to JSON string", e);
        }
    }

    @SuppressWarnings("unchecked")
    protected Map<String, Object> convertJsonStringToMap(String json) {
        if (json == null || json.isEmpty()) {
            return new HashMap<>();
        }

        try {
            return om.readValue(json, new TypeReference<Map<String, Object>>() {});
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error converting JSON string to Map", e);
        }
    }
}
