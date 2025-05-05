package wanted.domain.product.dto;

import java.util.List;

public record ProductListResponse(
        List<SimpleProductResponse> items,
        Pagination pagination
) {}
