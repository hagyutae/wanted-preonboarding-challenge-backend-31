package wanted.shop.product.dto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import wanted.shop.brand.domain.entity.Brand;
import wanted.shop.brand.domain.entity.BrandId;
import wanted.shop.category.domain.entity.Category;
import wanted.shop.product.domain.entity.*;
import wanted.shop.seller.domain.entity.Seller;
import wanted.shop.seller.domain.entity.SellerId;
import wanted.shop.tag.domain.entity.Tag;
import wanted.shop.tag.domain.entity.TagId;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Getter @Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = false)
public class ProductCreateRequest {

    private String name;
    private String slug;

    @JsonProperty("short_description")
    private String shortDescription;

    @JsonProperty("full_description")
    private String fullDescription;

    @JsonProperty("seller_id")
    private Long sellerId;

    public SellerId getSellerId() {
        return new SellerId(sellerId);
    }

    @JsonProperty("brand_id")
    private Long brandId;

    public BrandId getBrandId() {
        return new BrandId(brandId);
    }

    private String status;

    private Detail detail;
    private Price price;

    private List<ProductCategoryRequest> categories;

    @JsonProperty("option_groups")
    private List<OptionGroup> optionGroups;

    private List<Image> images;

    @JsonProperty("tags")
    private List<Long> tagIds;

    public List<TagId> getTagIds() {
        return tagIds.stream().map(TagId::new).toList();
    }

    public Product toProduct(Seller seller, Brand brand, List<Tag> tags, List<Category> categories) {
        Product product = Product.builder()
                .productData(new ProductData(name, slug, shortDescription, fullDescription))
                .status(status)
                .productTimestamps(ProductTimestamps.createNow())
                .seller(seller)
                .brand(brand)
                .build();

        product.setProductDetail(toDetail(product));
        product.setProductPrice(toPrice(product));
        product.addTags(toProductTags(product, tags));
        product.addImages(toImages(product));
        product.addOptionGroups(toOptionGroups(product));
        product.addProductCategories(toProductCategories(product, categories));

        return product;
    }

    private ProductDetail toDetail(Product product) {
        return ProductDetail.builder()
                .product(product)
                .weight(detail.getWeight())
                .dimensions(detail.getDimensions().toString()) // 혹은 JSON 변환
                .materials(detail.getMaterials())
                .countryOfOrigin(detail.getCountryOfOrigin())
                .warrantyInfo(detail.getWarrantyInfo())
                .careInstructions(detail.getCareInstructions())
                .additionalInfo(detail.getAdditionalInfo().toString()) // JSONB 문자열
                .build();
    }

    private ProductPrice toPrice(Product product) {
        return ProductPrice.builder()
                .product(product)
                .basePrice(price.getBasePrice())
                .salePrice(price.getSalePrice())
                .costPrice(price.getCostPrice())
                .currency(price.getCurrency())
                .taxRate(price.getTaxRate())
                .build();
    }

    private List<ProductTag> toProductTags(Product product, List<Tag> tags) {
        return tags.stream()
                .map(tag -> ProductTag.builder()
                        .product(product)
                        .tag(tag)
                        .build())
                .toList();
    }

    private List<ProductImage> toImages(Product product) {
        return images.stream()
                .map(img -> ProductImage.builder()
                        .product(product)
                        .url(img.getUrl())
                        .altText(img.getAltText())
                        .isPrimary(img.isPrimary())
                        .displayOrder(img.getDisplayOrder())
                        .optionId(img.getOptionId())
                        .build())
                .toList();
    }

    private List<ProductOptionGroup> toOptionGroups(Product product) {
        return optionGroups.stream()
                .map(group -> {
                    ProductOptionGroup og = ProductOptionGroup.builder()
                            .product(product)
                            .name(group.getName())
                            .displayOrder(group.getDisplayOrder())
                            .build();

                    List<ProductOption> options = group.getOptions().stream()
                            .map(opt -> ProductOption.builder()
                                    .optionGroup(og)
                                    .name(opt.getName())
                                    .additionalPrice(opt.getAdditionalPrice())
                                    .sku(opt.getSku())
                                    .stock(opt.getStock())
                                    .displayOrder(opt.getDisplayOrder())
                                    .build())
                            .toList();

                    og.setOptions(options);
                    return og;
                }).toList();
    }

    private List<ProductCategory> toProductCategories(Product product, List<Category> categories) {
        return categories.stream().map(category -> {
            return ProductCategory.builder()
                    .product(product)
                    .category(category)
                    .isPrimary(Boolean.FALSE)
                    .build();
        }).toList();
    }

    @Getter @Setter
    @NoArgsConstructor
    public static class Detail {
        private String weight;
        private Dimension dimensions;
        private String materials;

        @JsonProperty("country_of_origin")
        private String countryOfOrigin;

        @JsonProperty("warranty_info")
        private String warrantyInfo;

        @JsonProperty("care_instructions")
        private String careInstructions;

        @JsonProperty("additional_info")
        private Map<String, Object> additionalInfo;

        @Getter @Setter
        @NoArgsConstructor
        public static class Dimension {
            private int width;
            private int height;
            private int depth;
        }
    }

    @Getter @Setter
    @NoArgsConstructor
    public static class Price {
        @JsonProperty("base_price")
        private BigDecimal basePrice;

        @JsonProperty("sale_price")
        private int salePrice;

        @JsonProperty("cost_price")
        private int costPrice;

        private String currency;

        @JsonProperty("tax_rate")
        private int taxRate;
    }

    @Getter @Setter
    @NoArgsConstructor
    public static class ProductCategoryRequest {
        @JsonProperty("category_id")
        private Long categoryId;

        @JsonProperty("is_primary")
        private boolean isPrimary;
    }

    @Getter @Setter
    @NoArgsConstructor
    public static class OptionGroup {
        private String name;

        @JsonProperty("display_order")
        private int displayOrder;

        private List<Option> options;

        @Getter @Setter
        @NoArgsConstructor
        public static class Option {
            private String name;

            @JsonProperty("additional_price")
            private BigDecimal additionalPrice;

            private String sku;
            private int stock;

            @JsonProperty("display_order")
            private int displayOrder;
        }
    }

    @Getter @Setter
    @NoArgsConstructor
    public static class Image {
        private String url;

        @JsonProperty("alt_text")
        private String altText;

        @JsonProperty("is_primary")
        private boolean isPrimary;

        @JsonProperty("display_order")
        private int displayOrder;

        @JsonProperty("option_id")
        private Long optionId;
    }
}

