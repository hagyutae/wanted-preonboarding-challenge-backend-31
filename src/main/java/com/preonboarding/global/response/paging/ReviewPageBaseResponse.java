package com.preonboarding.global.response.paging;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewPageBaseResponse<T,V> {
    private boolean success;
    private PagingDataDto<T> data;
    private V summary;
    private String message;
    private PaginationDto pagination;
}
