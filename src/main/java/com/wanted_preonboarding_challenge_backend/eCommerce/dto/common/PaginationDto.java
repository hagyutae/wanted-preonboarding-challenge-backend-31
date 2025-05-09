package com.wanted_preonboarding_challenge_backend.eCommerce.dto.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaginationDto {
    private long totalItems;
    private int totalPages;
    private int currentPage;
    private int perPage;
}
