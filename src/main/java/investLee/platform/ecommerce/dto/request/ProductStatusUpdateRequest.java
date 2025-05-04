package investLee.platform.ecommerce.dto.request;

import investLee.platform.ecommerce.domain.ProductStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductStatusUpdateRequest {
    @NotNull
    private ProductStatus status;
}