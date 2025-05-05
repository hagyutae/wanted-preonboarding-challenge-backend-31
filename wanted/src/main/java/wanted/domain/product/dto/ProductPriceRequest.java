package wanted.domain.product.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record ProductPriceRequest(
        @NotNull(message = "기본 가격은 필수입니다.")
        @DecimalMin(value = "0.0", inclusive = true, message = "기본 가격은 0 이상이어야 합니다.")
        BigDecimal basePrice,

        @DecimalMin(value = "0.0", inclusive = true, message = "판매 가격은 0 이상이어야 합니다.")
        BigDecimal salePrice,

        @DecimalMin(value = "0.0", inclusive = true, message = "원가 가격은 0 이상이어야 합니다.")
        BigDecimal costPrice,

        @NotBlank(message = "통화 단위는 필수입니다.")
        @Size(max = 3, message = "통화 코드는 3자 이하여야 합니다.")
        String currency,

        @DecimalMin(value = "0.0", inclusive = true, message = "세율은 0 이상이어야 합니다.")
        BigDecimal taxRate
) {}
