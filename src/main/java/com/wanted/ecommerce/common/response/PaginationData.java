package com.wanted.ecommerce.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PaginationData<T> {

    private List<T> items;
    private Pagination pagination;

    public static <T> PaginationData<T> of(List<T> items, Pagination pagination) {
        return new PaginationData<>(items, pagination);
    }
}
