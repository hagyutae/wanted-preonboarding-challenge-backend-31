package investLee.platform.ecommerce.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductOptionGroupCreateRequest {
    @NotBlank
    private String name;

    @NotNull
    private Integer displayOrder;

    @NotEmpty
    private List<ProductOptionCreateRequestDTO> options;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProductOptionCreateRequestDTO {
        @NotBlank private String name;
        @NotNull private BigDecimal additionalPrice;
        @NotBlank private String sku;
        @NotNull private Integer stock;
        @NotNull private Integer displayOrder;
    }
}