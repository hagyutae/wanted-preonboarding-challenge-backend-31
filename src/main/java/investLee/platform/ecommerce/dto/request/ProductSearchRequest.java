package investLee.platform.ecommerce.dto.request;

import investLee.platform.ecommerce.dto.ProductSortType;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductSearchRequest {
    @NotBlank
    private String keyword;

    private ProductSortType sortType = ProductSortType.NEWEST;

    private int page = 0;
    private int size = 20;
}