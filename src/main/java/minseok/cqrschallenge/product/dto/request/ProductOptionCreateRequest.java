package minseok.cqrschallenge.product.dto.request;

import jakarta.validation.constraints.NotBlank;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductOptionCreateRequest {
    private Long optionGroupId;
    
    @NotBlank(message = "옵션명은 필수 항목입니다.")
    private String name;
    
    private BigDecimal additionalPrice;
    
    @NotBlank(message = "SKU는 필수 항목입니다.")
    private String sku;
    
    private Integer stock;
    private Integer displayOrder;
}
