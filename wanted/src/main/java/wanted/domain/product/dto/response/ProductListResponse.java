package wanted.domain.product.dto.response;

import java.util.List;

public record ProductListResponse(
        List<SimpleProductResponse> items,
        Pagination pagination
) {}
