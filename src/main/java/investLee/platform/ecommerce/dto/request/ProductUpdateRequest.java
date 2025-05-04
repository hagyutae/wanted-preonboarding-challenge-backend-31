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
public class ProductUpdateRequest {

    @NotBlank private String name;
    @NotBlank private String slug;
    private String shortDescription;
    private String fullDescription;

    @NotNull private ProductStatus status;

    @NotNull private Long sellerId;
    @NotNull private Long brandId;

    @NotNull private ProductPriceDTO price;

    private List<Long> categoryIds;
    private Long primaryCategoryId;

    @Data
    public static class ProductPriceDTO {
        private BigDecimal basePrice;
        private BigDecimal salePrice;
        private BigDecimal costPrice;
        private String currency;
        private BigDecimal taxRate;
    }
}
