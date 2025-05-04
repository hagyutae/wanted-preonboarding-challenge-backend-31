package investLee.platform.ecommerce.dto.request;

import investLee.platform.ecommerce.domain.ProductStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductCreateRequest {

    @NotBlank private String name;
    @NotBlank private String slug;
    private String shortDescription;
    private String fullDescription;

    @NotNull
    private ProductStatus status;

    @NotNull private Long sellerId;
    @NotNull private Long brandId;

    @NotNull private ProductPriceDTO price;
    private List<Long> categoryIds;
    private Long primaryCategoryId;

    private List<ProductOptionGroupDTO> optionGroups;
    private List<ProductImageDTO> images;

    @Data
    @Builder
    public static class ProductPriceDTO {
        private BigDecimal basePrice;
        private BigDecimal salePrice;
        private BigDecimal costPrice;
        private String currency;
        private BigDecimal taxRate;
    }

    @Data
    public static class ProductOptionGroupDTO {
        private String name;
        private int displayOrder;
        private List<ProductOptionDTO> options;

        @Data
        public static class ProductOptionDTO {
            private String name;
            private BigDecimal additionalPrice;
            private String sku;
            private int stock;
            private int displayOrder;
        }
    }

    @Data
    public static class ProductImageDTO {
        private String url;
        private String altText;
        private boolean isPrimary;
        private int displayOrder;
        private Long optionId;  // null 허용
    }
}