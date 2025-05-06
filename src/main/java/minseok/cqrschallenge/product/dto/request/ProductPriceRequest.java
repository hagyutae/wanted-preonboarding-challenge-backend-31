package minseok.cqrschallenge.product.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductPriceRequest {

    @NotNull(message = "기본 가격은 필수 항목입니다.")
    @Positive(message = "기본 가격은 0보다 커야 합니다.")
    private BigDecimal basePrice;

    @Positive(message = "할인 가격은 0보다 커야 합니다.")
    private BigDecimal salePrice;

    @Positive(message = "원가는 0보다 커야 합니다.")
    private BigDecimal costPrice;

    private String currency;

    @Positive(message = "세율은 0보다 커야 합니다.")
    private BigDecimal taxRate;
}