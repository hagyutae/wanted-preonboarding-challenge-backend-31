package lkdcode.wanted.ecommerce.modules.products.application.usecase.query.dto.list;

import lkdcode.wanted.ecommerce.modules.products.application.usecase.query.value.Pagination;
import lombok.Builder;

import java.util.List;

@Builder
public record QueryProductListResult(
    List<QueryProductDTO> items,
    Pagination pagination
) {
}