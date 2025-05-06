package com.sandro.wanted_shop.product.dto;

import com.sandro.wanted_shop.category.CategoryDto;
import com.sandro.wanted_shop.common.entity.BaseEntity;
import com.sandro.wanted_shop.product.entity.*;
import com.sandro.wanted_shop.product.entity.enums.Currency;
import com.sandro.wanted_shop.product.entity.enums.ProductStatus;
import com.sandro.wanted_shop.product.entity.relation.ProductOptionGroup;
import com.sandro.wanted_shop.product.entity.relation.ProductTag;
import com.sandro.wanted_shop.review.dto.ReviewDto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public record ProductDto(
        Long id,
        String name,
        String slug,
        String shortDescription,
        String fullDescription,
        ProductStatus status,
        Price price,
        Detail detail,
        List<CategoryDto> categories,
        List<OptionGroup> optionGroups,
        List<Image> images,
        List<Tag> tags,
        List<ReviewDto> reviews
) {
    public static ProductDto from(Product product) {
        return new ProductDto(
                product.getId(),
                product.getName(),
                product.getSlug(),
                product.getShortDescription(),
                product.getFullDescription(),
                product.getStatus(),
                Price.from(product.getPrice()),
                Detail.from(product.getDetail()),
                CategoryDto.from(product.getCategories()),
                OptionGroup.from(product.getOptionGroups()),
                Image.from(product.getImages()),
                Tag.from(product.getTags()),
                ReviewDto.from(product.getReviews())
        );
    }

    public record Price(
            BigDecimal basePrice,
            BigDecimal salePrice,
            BigDecimal costPrice,
            Currency currency,
            BigDecimal taxRate
    ) {
        public static Price from(ProductPrice price) {
            return new Price(
                    price.getBasePrice(),
                    price.getSalePrice(),
                    price.getCostPrice(),
                    price.getCurrency(),
                    price.getTaxRate()
            );
        }
    }

    public record Detail(
            BigDecimal weight,
            Dimensions dimensions,
            String materials,
            String countryOfOrigin,
            String warrantyInfo,
            String careInstructions,
            Map<String, Object> additionalInfo
    ) {
        public static Detail from(ProductDetail detail) {
            return new Detail(
                    detail.getWeight(),
                    detail.getDimensions(),
                    detail.getMaterials(),
                    detail.getCountryOfOrigin(),
                    detail.getWarrantyInfo(),
                    detail.getCareInstructions(),
                    detail.getAdditionalInfo()
            );
        }
    }

    public record OptionGroup(
            String name,
            Integer displayOrder,
            List<Option> options
    ) {
        public static List<OptionGroup> from(List<ProductOptionGroup> optionGroups) {
            return optionGroups.stream()
                    .map(optionGroup -> new OptionGroup(
                            optionGroup.getName(),
                            optionGroup.getDisplayOrder(),
                            Option.from(optionGroup.getOptions()))
                    ).toList();
        }

        public record Option(
                String name,
                BigDecimal additionalPrice,
                String sku,
                Integer stock,
                Integer displayOrder
        ) {
            public static List<Option> from(List<ProductOption> options) {
                return options.stream()
                        .map(option -> new Option(
                                option.getName(),
                                option.getAdditionalPrice(),
                                option.getSku(),
                                option.getStock(),
                                option.getDisplayOrder()
                        )).toList();
            }
        }
    }

    public record Image(
            String url,
            String altText,
            Boolean isPrimary,
            Integer displayOrder,
            Long optionId
    ) {
        public static List<Image> from(List<ProductImage> images) {
            return images.stream()
                    .map(image -> new Image(
                            image.getUrl(),
                            image.getAltText(),
                            image.getIsPrimary(),
                            image.getDisplayOrder(),
                            Optional.ofNullable(image.getOption())
                                    .map(BaseEntity::getId)
                                    .orElse(null)
                    )).toList();
        }
    }

    public record Tag(
            String name,
            String slug
    ) {
        public static List<Tag> from(List<ProductTag> tags) {
            return tags.stream()
                    .map(productTag -> {
                        var tag = productTag.getTag();
                        return new Tag(
                                tag.getName(),
                                tag.getSlug()
                        );
                    }).toList();
        }
    }
}
