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
    private String short_description;
    private BigDecimal base_price;
    private BigDecimal sale_price;
    private String currency;
    private ProductImageSummary primary_image;
    private BrandSummary brand;
    private SellerSummary seller;
    private double rating;
    private int review_count;
    private boolean in_stock;
    private String status;
    private LocalDateTime created_at;
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProductImageSummary {
        private String url;
        private String alt_text;
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