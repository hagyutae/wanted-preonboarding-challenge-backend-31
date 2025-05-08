package com.pawn.wantedcqrs.product.dto;

import com.pawn.wantedcqrs.product.entity.Currency;
import com.pawn.wantedcqrs.product.entity.ProductStatus;
import com.pawn.wantedcqrs.productOptionGroup.dto.ProductOptionDto;
import com.pawn.wantedcqrs.productOptionGroup.dto.ProductOptionGroupDto;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class CreateProductRequest {

    private final String name;

    private final String slug;

    private final String shortDescription;

    private final String fullDescription;

    private final Long sellerId;

    private final Long brandId;

    private final ProductStatus status;

    private final Detail detail;

    private final Price price;

    private final List<Category> categories;

    private final List<OptionGroup> optionGroups;

    private final List<Image> images;

    private final List<Long> tags;

    @Builder
    public CreateProductRequest(String name, String slug, String shortDescription, String fullDescription,
                                Long sellerId, Long brandId, ProductStatus status, Detail detail,
                                Price price, List<Category> categories, List<OptionGroup> optionGroups,
                                List<Image> images, List<Long> tags) {
        this.name = name;
        this.slug = slug;
        this.shortDescription = shortDescription;
        this.fullDescription = fullDescription;
        this.sellerId = sellerId;
        this.brandId = brandId;
        this.status = status;
        this.detail = detail;
        this.price = price;
        this.categories = categories;
        this.optionGroups = optionGroups;
        this.images = images;
        this.tags = tags;
    }

    @Getter
    public static class Detail {

        private final BigDecimal weight;

        private final Dimensions dimensions;

        private final String materials;

        private final String countryOfOrigin;

        private final String warrantyInfo;

        private final String careInstructions;

        private final AdditionalInfo additionalInfo;

        @Builder
        public Detail(BigDecimal weight, Dimensions dimensions, String materials, String countryOfOrigin,
                      String warrantyInfo, String careInstructions, AdditionalInfo additionalInfo) {
            this.weight = weight;
            this.dimensions = dimensions;
            this.materials = materials;
            this.countryOfOrigin = countryOfOrigin;
            this.warrantyInfo = warrantyInfo;
            this.careInstructions = careInstructions;
            this.additionalInfo = additionalInfo;
        }

        public static Detail of(BigDecimal weight, Dimensions dimensions, String materials, String countryOfOrigin,
                                String warrantyInfo, String careInstructions, AdditionalInfo additionalInfo) {
            return new Detail(weight, dimensions, materials, countryOfOrigin, warrantyInfo, careInstructions, additionalInfo);
        }
    }

    @Getter
    public static class Dimensions {

        private final int width;

        private final int height;

        private final int depth;

        @Builder
        public Dimensions(int width, int height, int depth) {
            this.width = width;
            this.height = height;
            this.depth = depth;
        }

        public static Dimensions of(int width, int height, int depth) {
            return new Dimensions(width, height, depth);
        }
    }

    @Getter
    public static class AdditionalInfo {

        private final boolean assemblyRequired;

        private final String assemblyTime;

        @Builder
        public AdditionalInfo(boolean assemblyRequired, String assemblyTime) {
            this.assemblyRequired = assemblyRequired;
            this.assemblyTime = assemblyTime;
        }

        public static AdditionalInfo of(boolean assemblyRequired, String assemblyTime) {
            return new AdditionalInfo(assemblyRequired, assemblyTime);
        }
    }

    @Getter
    public static class Price {

        private final BigDecimal basePrice;

        private final BigDecimal salePrice;

        private final BigDecimal costPrice;

        private final Currency currency;

        private final BigDecimal taxRate;

        @Builder
        public Price(BigDecimal basePrice, BigDecimal salePrice, BigDecimal costPrice, Currency currency, BigDecimal taxRate) {
            this.basePrice = basePrice;
            this.salePrice = salePrice;
            this.costPrice = costPrice;
            this.currency = currency;
            this.taxRate = taxRate;
        }

        public static Price of(BigDecimal basePrice, BigDecimal salePrice, BigDecimal costPrice, Currency currency, BigDecimal taxRate) {
            return new Price(basePrice, salePrice, costPrice, currency, taxRate);
        }
    }

    @Getter
    public static class Category {

        private final Long categoryId;

        private final boolean isPrimary;

        @Builder
        public Category(Long categoryId, boolean isPrimary) {
            this.categoryId = categoryId;
            this.isPrimary = isPrimary;
        }

        public static Category of(Long categoryId, boolean isPrimary) {
            return new Category(categoryId, isPrimary);
        }

    }

    @Getter
    public static class OptionGroup {

        private final String name;

        private final int displayOrder;

        private final List<Option> options;

        @Builder
        public OptionGroup(String name, int displayOrder, List<Option> options) {
            this.name = name;
            this.displayOrder = displayOrder;
            this.options = options;
        }

        public static OptionGroup of(String name, int displayOrder, List<Option> options) {
            return new OptionGroup(name, displayOrder, options);
        }

    }

    @Getter
    public static class Option {

        private final String name;

        private final int additionalPrice;

        private final String sku;

        private final int stock;

        private final int displayOrder;

        @Builder
        public Option(String name, int additionalPrice, String sku, int stock, int displayOrder) {
            this.name = name;
            this.additionalPrice = additionalPrice;
            this.sku = sku;
            this.stock = stock;
            this.displayOrder = displayOrder;
        }

        public static Option of(String name, int additionalPrice, String sku, int stock, int displayOrder) {
            return new Option(name, additionalPrice, sku, stock, displayOrder);
        }

    }

    @Getter
    public static class Image {

        private final String url;

        private final String altText;

        private final boolean isPrimary;

        private final int displayOrder;

        private final Long optionId;

        @Builder
        public Image(String url, String altText, boolean isPrimary, int displayOrder, Long optionId) {
            this.url = url;
            this.altText = altText;
            this.isPrimary = isPrimary;
            this.displayOrder = displayOrder;
            this.optionId = optionId;
        }

        public static Image of(String url, String altText, boolean isPrimary, int displayOrder, Long optionId) {
            return new Image(url, altText, isPrimary, displayOrder, optionId);
        }

    }

    public ProductDto toProductDto() {
        ProductDetailDto detailDto = new ProductDetailDto();
        Detail detailParam = this.getDetail();
        detailDto.setWeight(detailParam.getWeight());
        detailDto.setMaterials(detailParam.getMaterials());
        detailDto.setCountryOfOrigin(detailParam.getCountryOfOrigin());
        detailDto.setWarrantyInfo(detailParam.getWarrantyInfo());
        detailDto.setCareInstructions(detailParam.getCareInstructions());

        var dimensionParams = this.getDetail().getDimensions();
        detailDto.setDimensions(
                new ProductDetailDto.Dimensions(
                        dimensionParams.getWidth(),
                        dimensionParams.getHeight(),
                        dimensionParams.getDepth()
                ));

        var additionalInfoParam = this.getDetail().getAdditionalInfo();
        detailDto.setAdditionalInfo(
                new ProductDetailDto.AdditionalInfo(
                        additionalInfoParam.isAssemblyRequired(),
                        additionalInfoParam.getAssemblyTime()
                ));

        ProductPriceDto priceDto = new ProductPriceDto();
        Price priceParam = this.getPrice();
        priceDto.setBasePrice(priceParam.getBasePrice());
        priceDto.setSalePrice(priceParam.getSalePrice());
        priceDto.setCostPrice(priceParam.getCostPrice());
        priceDto.setTaxRate(priceParam.getTaxRate());
        priceDto.setCurrency(priceParam.getCurrency());

        ProductDto productDto = new ProductDto();
        productDto.setName(name);
        productDto.setSlug(slug);
        productDto.setShortDescription(shortDescription);
        productDto.setFullDescription(fullDescription);
        productDto.setSellerId(sellerId);
        productDto.setBrandId(brandId);
        productDto.setStatus(status);
        productDto.setDetail(detailDto);
        productDto.setPrice(priceDto);

        return productDto;
    }

    public List<ProductOptionGroupDto> toOptionGroupDtos() {
        return this.optionGroups.stream()
                .map(group -> {
                    ProductOptionGroupDto dto = new ProductOptionGroupDto();
                    dto.setName(group.getName());
                    dto.setDisplayOrder(group.getDisplayOrder());

                    List<ProductOptionDto> optionDtos = group.getOptions().stream()
                            .map(opt -> {
                                ProductOptionDto optionDto = new ProductOptionDto();
                                optionDto.setName(opt.getName());
                                optionDto.setAdditionalPrice(BigDecimal.valueOf(opt.getAdditionalPrice()));
                                optionDto.setSku(opt.getSku());
                                optionDto.setStock(opt.getStock());
                                optionDto.setDisplayOrder(opt.getDisplayOrder());
                                return optionDto;
                            })
                            .collect(Collectors.toList());

                    dto.setOptions(optionDtos);
                    return dto;
                })
                .collect(Collectors.toList());
    }

    public List<ProductCategoryDto> toProductCategoryDtos(Long productId) {
        return this.categories.stream()
                .map(category -> {
                    ProductCategoryDto dto = new ProductCategoryDto();
                    dto.setProductId(productId);
                    dto.setCategoryId(category.getCategoryId());
                    dto.setPrimary(category.isPrimary());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    public List<ProductTagDto> toProductTagDtos(Long productId) {
        return this.tags.stream()
                .map(tagId -> {
                    ProductTagDto dto = new ProductTagDto();
                    dto.setProductId(productId);
                    dto.setTagId(tagId);
                    return dto;
                })
                .collect(Collectors.toList());
    }


}
