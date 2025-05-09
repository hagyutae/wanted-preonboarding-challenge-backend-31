package wanted.shop.common.api;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;

import java.util.List;

@Getter
@JsonPropertyOrder({ "items", "pagination" })
public class PaginatedData<T> {
    private final List<T> items;
    private final Pagination pagination;

    public PaginatedData(List<T> items, Pagination pagination) {
        this.items = items;
        this.pagination = pagination;
    }
}
