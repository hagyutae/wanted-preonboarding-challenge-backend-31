package com.challenge.onboarding.common.global;

public record Pagination(
        int totalItems,
        int totalPages,
        int currentPage,
        int perPage
) {
}
