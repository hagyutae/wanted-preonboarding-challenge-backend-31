package investLee.platform.ecommerce.dto.request;

import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

@Getter
public class ProductCreateRequest {
    private String name;
    private String slug;
    private String shortDescription;
    private String fullDescription;
    private Long sellerId;
    private Long brandId;
    private String status;

    private ProductDetailDto detail;
    private ProductPriceDto price;
    private List<OptionGroupDto> optionGroups;
    private List<ProductImageDto> images;

    @Getter
    public static class ProductDetailDto {
        private BigDecimal weight;
        private String dimensions;
        private String materials;
        private String countryOfOrigin;
        private String warrantyInfo;
        private String careInstructions;
        private String additionalInfo;
    }

    @Getter
    public static class ProductPriceDto {
        private BigDecimal basePrice;
        private BigDecimal salePrice;
        private BigDecimal costPrice;
        private String currency;
        private BigDecimal taxRate;
    }

    @Getter
    public static class OptionGroupDto {
        private String name;
        private Integer displayOrder;
        private List<ProductOptionDto> options;

        @Getter
        public static class ProductOptionDto {
            private String name;
            private BigDecimal additionalPrice;
            private String sku;
            private Integer stock;
            private Integer displayOrder;
        }
    }

    @Getter
    public static class ProductImageDto {
        private String url;
        private String altText;
        private Boolean isPrimary;
        private Integer displayOrder;
    }
}
