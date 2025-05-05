package com.dino.cqrs_challenge.global.response;

import io.swagger.v3.oas.annotations.media.Schema;
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
public class PaginatedApiResponse<T> {

    @Schema(description = "응답 데이터 배열")
    private List<T> items;

    @Schema(description = "페이지네이션 정보")
    private Pagination pagination;

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "페이지네이션 메타데이터")
    public static class Pagination {

        @Schema(description = "전체 항목 수", example = "100")
        private long totalItems;

        @Schema(description = "전체 페이지 수", example = "10")
        private int totalPages;

        @Schema(description = "현재 페이지 번호", example = "1")
        private int currentPage;

        @Schema(description = "페이지당 항목 수", example = "10")
        private int perPage;
    }
}
