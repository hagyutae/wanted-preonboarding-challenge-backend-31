package wanted.domain.product.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record ProductOptionRequest(
        @NotBlank(message = "옵션 이름은 필수입니다.")
        @Size(max = 255, message = "옵션 이름은 255자 이하로 입력해주세요.")
        String name,

        @DecimalMin(value = "0.0", inclusive = true, message = "추가 금액은 0 이상이어야 합니다.")
        BigDecimal additionalPrice,

        @Size(max = 100, message = "SKU는 100자 이하로 입력해주세요.")
        String sku,

        @NotNull(message = "재고 수량은 필수입니다.")
        @Min(value = 0, message = "재고 수량은 0 이상이어야 합니다.")
        Integer stock,

        @NotNull(message = "표시 순서는 필수입니다.")
        @Min(value = 0, message = "표시 순서는 0 이상이어야 합니다.")
        Integer displayOrder
) {}