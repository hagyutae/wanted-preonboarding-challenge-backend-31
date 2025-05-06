package minseok.cqrschallenge.main.dto.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import minseok.cqrschallenge.product.dto.response.ProductListResponse;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MainPageResponse {

    private List<ProductListResponse> newProducts;

    private List<ProductListResponse> popularProducts;

    private List<FeaturedCategoryResponse> featuredCategories;
}