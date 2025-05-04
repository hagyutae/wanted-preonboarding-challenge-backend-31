package investLee.platform.ecommerce.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MainPageResponse {

    private List<ProductSummaryResponse> newProducts;
    private Map<String, List<ProductSummaryResponse>> popularProductsByCategory;
}