package minseok.cqrschallenge.product.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductListResponse {

    private Long id;

    private String name;

    private String slug;

    private String shortDescription;

    private BigDecimal basePrice;

    private BigDecimal salePrice;

    private String currency;

    private ProductImageSummary primaryImage;

    private BrandSummary brand;

    private SellerSummary seller;

    private double rating;

    private int reviewCount;

    private boolean inStock;

    private String status;

    private LocalDateTime createdAt;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProductImageSummary {

        private String url;

        private String altText;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BrandSummary {

        private Long id;

        private String name;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SellerSummary {

        private Long id;

        private String name;
    }
}