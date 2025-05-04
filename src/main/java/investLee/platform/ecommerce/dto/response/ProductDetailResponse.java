package investLee.platform.ecommerce.dto.response;

import investLee.platform.ecommerce.domain.ProductStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDetailResponse {

    private Long id;
    private String name;
    private String slug;
    private String shortDescription;
    private String fullDescription;
    private ProductStatus status;

    private PriceDTO price;
    private BrandDTO brand;
    private SellerDTO seller;

    private List<CategoryDTO> categories;
    private List<OptionGroupDTO> optionGroups;
    private List<ImageDTO> images;

    private Double averageRating;

    // --- Nested DTOs ---
    @Data
    @AllArgsConstructor
    public static class PriceDTO {
        private BigDecimal basePrice;
        private BigDecimal salePrice;
        private BigDecimal costPrice;
        private String currency;
        private BigDecimal taxRate;
    }

    @Data
    @AllArgsConstructor
    public static class BrandDTO {
        private Long id;
        private String name;
        private String logoUrl;
    }

    @Data
    @AllArgsConstructor
    public static class SellerDTO {
        private Long id;
        private String name;
        private Double rating;
    }

    @Data
    @AllArgsConstructor
    public static class CategoryDTO {
        private Long id;
        private String name;
        private int level;
    }

    @Data
    @AllArgsConstructor
    public static class OptionGroupDTO {
        private String name;
        private List<OptionDTO> options;
    }

    @Data
    @AllArgsConstructor
    public static class OptionDTO {
        private String name;
        private BigDecimal additionalPrice;
        private int stock;
        private String sku;
    }

    @Data
    @AllArgsConstructor
    public static class ImageDTO {
        private String url;
        private boolean isPrimary;
        private String altText;
    }
}