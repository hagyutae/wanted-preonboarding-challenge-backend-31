package com.shopping.mall.product.dto.response;

import com.shopping.mall.product.entity.*;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
public class ProductDetailResponse {

    private Long id;
    private String name;
    private String slug;
    private String shortDescription;
    private String fullDescription;

    private ProductDetailResponse.Detail detail;
    private ProductDetailResponse.Price price;

    private List<OptionGroup> optionGroups;
    private List<Image> images;
    private List<Tag> tags;
    private List<Category> categories;

    @Getter
    @Builder
    public static class Detail {
        private Double weight;
        private String dimensions;
        private String materials;
        private String countryOfOrigin;
        private String warrantyInfo;
        private String careInstructions;
        private String additionalInfo;
    }

    @Getter
    @Builder
    public static class Price {
        private Integer basePrice;
        private Integer salePrice;
        private Integer costPrice;
        private String currency;
        private Integer taxRate;
    }

    @Getter
    @Builder
    public static class OptionGroup {
        private String name;
        private Integer displayOrder;
        private List<Option> options;
    }

    @Getter
    @Builder
    public static class Option {
        private String name;
        private Integer additionalPrice;
        private String sku;
        private Integer stock;
        private Integer displayOrder;
    }

    @Getter
    @Builder
    public static class Image {
        private String url;
        private String altText;
        private Boolean isPrimary;
        private Integer displayOrder;
        private Long optionId;
    }

    @Getter
    @Builder
    public static class Tag {
        private Long id;
        private String name;
    }

    @Getter
    @Builder
    public static class Category {
        private Long id;
        private String name;
    }

    public static ProductDetailResponse from(Product product) {
        return ProductDetailResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .slug(product.getSlug())
                .shortDescription(product.getShortDescription())
                .fullDescription(product.getFullDescription())
                .detail(Detail.builder()
                        .weight(product.getProductDetail().getWeight())
                        .dimensions(product.getProductDetail().getDimensions())
                        .materials(product.getProductDetail().getMaterials())
                        .countryOfOrigin(product.getProductDetail().getCountryOfOrigin())
                        .warrantyInfo(product.getProductDetail().getWarrantyInfo())
                        .careInstructions(product.getProductDetail().getCareInstructions())
                        .additionalInfo(product.getProductDetail().getAdditionalInfo())
                        .build())
                .price(Price.builder()
                        .basePrice(product.getProductPrice().getBasePrice())
                        .salePrice(product.getProductPrice().getSalePrice())
                        .costPrice(product.getProductPrice().getCostPrice())
                        .currency(product.getProductPrice().getCurrency())
                        .taxRate(product.getProductPrice().getTaxRate())
                        .build())
                .optionGroups(product.getOptionGroups().stream().map(group ->
                        OptionGroup.builder()
                                .name(group.getName())
                                .displayOrder(group.getDisplayOrder())
                                .options(group.getOptions().stream().map(option ->
                                        Option.builder()
                                                .name(option.getName())
                                                .additionalPrice(option.getAdditionalPrice())
                                                .sku(option.getSku())
                                                .stock(option.getStock())
                                                .displayOrder(option.getDisplayOrder())
                                                .build()
                                ).collect(Collectors.toList()))
                                .build()
                ).collect(Collectors.toList()))
                .images(product.getProductImages().stream().map(image ->
                        Image.builder()
                                .url(image.getUrl())
                                .altText(image.getAltText())
                                .isPrimary(image.getIsPrimary())
                                .displayOrder(image.getDisplayOrder())
                                .optionId(image.getOption() != null ? image.getOption().getId() : null)
                                .build()
                ).collect(Collectors.toList()))
                .tags(product.getProductTags().stream().map(tag ->
                        Tag.builder()
                                .id(tag.getTag().getId())
                                .name(tag.getTag().getName())
                                .build()
                ).collect(Collectors.toList()))
                .categories(product.getProductCategories().stream().map(category ->
                        Category.builder()
                                .id(category.getCategory().getId())
                                .name(category.getCategory().getName())
                                .build()
                ).collect(Collectors.toList()))
                .build();
    }
}