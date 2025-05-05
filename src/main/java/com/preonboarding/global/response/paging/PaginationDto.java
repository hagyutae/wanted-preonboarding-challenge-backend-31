package com.preonboarding.global.response.paging;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaginationDto {
    private long totalItems;
    private int totalPages;
    private int currentPage;
    private int perPage;

    public static <T> PaginationDto from(Page<T> fetchedList, int currentPage, int perPage) {
        return PaginationDto.builder()
                .totalItems(fetchedList.getTotalElements())
                .totalPages(fetchedList.getTotalPages())
                .currentPage(currentPage+1)
                .perPage(perPage)
                .build();
    }
}
