package wanted.domain.product.dto;

import java.util.List;

public record ProductSearchCondition(
        Integer page,
        Integer perPage,
        String sort,
        String status,
        Integer minPrice,
        Integer maxPrice,
        List<Integer> category,
        Integer seller,
        Integer brand,
        Boolean inStock,
        String search
) {}
