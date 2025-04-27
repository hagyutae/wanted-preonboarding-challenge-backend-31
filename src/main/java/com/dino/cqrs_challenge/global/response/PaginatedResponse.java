package com.dino.cqrs_challenge.global.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 공통 페이지네이션 포함 성공 응답 DTO
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaginatedResponse<T> {
    private List<T> items;
    private Pagination pagination;

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Pagination {
        private long totalItems;
        private int totalPages;
        private int currentPage;
        private int perPage;
    }
}
