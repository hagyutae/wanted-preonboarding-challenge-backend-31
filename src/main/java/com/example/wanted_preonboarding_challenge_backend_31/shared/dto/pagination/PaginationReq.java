package com.example.wanted_preonboarding_challenge_backend_31.shared.dto.pagination;

public record PaginationReq(
        int page,
        int perPage
) {

    public long getOffset() {
        return (long) (page - 1) * perPage;
    }
}
