package com.preonboarding.global.response.paging;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PageBaseResponse<T,V> {
    private boolean success;
    private PagingDataDto<T> data;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private V summary;
    private String message;
    private PaginationDto pagination;
}
