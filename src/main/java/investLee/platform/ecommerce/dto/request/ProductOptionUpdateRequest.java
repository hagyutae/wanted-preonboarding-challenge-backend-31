package investLee.platform.ecommerce.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductOptionUpdateRequest {
    @NotBlank private String name;
    @NotNull private BigDecimal additionalPrice;
    @NotBlank private String sku;
    @NotNull private Integer stock;
    @NotNull private Integer displayOrder;
}