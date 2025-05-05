package com.example.cqrsapp.common.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@AllArgsConstructor
@Data
public class PaginationDto {
    private Long totalItems;
    private int totalPages;
    private int currentPage;
    private int perPage;
}