package minseok.cqrschallenge.product.dto.response;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductOptionResponse {

    private Long id;

    private Long optionGroupId;

    private String name;

    private BigDecimal additionalPrice;

    private String sku;

    private Integer stock;

    private Integer displayOrder;
}