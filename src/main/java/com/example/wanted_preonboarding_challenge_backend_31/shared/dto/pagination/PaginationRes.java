package com.example.wanted_preonboarding_challenge_backend_31.shared.dto.pagination;

public record PaginationRes(
        int totalItems,
        int totalPages,
        int currentPage,
        int perPage
) {

    public static PaginationRes of(int totalItems, int currentPage, int perPage) {
        int totalPages = (int) Math.ceil((double) totalItems / perPage);
        return new PaginationRes(totalItems, totalPages, currentPage, perPage);
    }
}
