package lkdcode.wanted.ecommerce.modules.products.application.usecase.query.dto.list;

import lombok.Builder;

import java.util.List;
import java.util.stream.Stream;

@Builder
public record QueryProductDTOList(
    List<QueryProductDTO> list
) {

    public Stream<QueryProductDTO> stream() {
        return list.stream();
    }
}