package cqrs.precourse.response;

import java.util.List;

public class PagedResponse<T> {
    private List<T> items;
    private Pagination pagination;

    public PagedResponse(List<T> items, Pagination pagination) {
        this.items = items;
        this.pagination = pagination;
    }
}
