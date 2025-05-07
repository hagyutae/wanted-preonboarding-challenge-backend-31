package wanted.domain.category.dto.response;

import wanted.domain.product.dto.response.Pagination;
import wanted.domain.product.dto.response.SimpleProductResponse;

import java.util.List;

public record CategoryProductResponse(
        CategoryResponse category,
        List<SimpleProductResponse> items,
        Pagination pagination
) {
}
