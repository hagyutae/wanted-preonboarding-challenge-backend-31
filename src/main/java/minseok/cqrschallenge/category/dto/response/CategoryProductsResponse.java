package minseok.cqrschallenge.category.dto.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import minseok.cqrschallenge.common.dto.PaginationResponse;
import minseok.cqrschallenge.product.dto.response.ProductListResponse;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryProductsResponse {

    private CategoryDetailResponse category;

    private List<ProductListResponse> items;

    private PaginationResponse.Pagination pagination;
}