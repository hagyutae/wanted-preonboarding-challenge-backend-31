package investLee.platform.ecommerce.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductSummaryResponse {

    private Long id;
    private String name;
    private String slug;
    private BigDecimal basePrice;
    private BigDecimal salePrice;
    private String thumbnailUrl;
    private String brandName;
    private Double averageRating;
}