package sample.challengewanted.dto.product;

import org.springframework.data.domain.Page;
import sample.challengewanted.dto.common.PaginationResponse;

import java.util.List;

public record PagedResponse<T>(
        List<T> items,
        PaginationResponse pagination
) {
    public static <T> PagedResponse<T> from(Page<T> page) {
        return new PagedResponse<>(
                page.getContent(),
                PaginationResponse.from(page)
        );
    }
}