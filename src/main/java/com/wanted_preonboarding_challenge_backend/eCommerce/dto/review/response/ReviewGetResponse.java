package com.wanted_preonboarding_challenge_backend.eCommerce.dto.review.response;

import com.wanted_preonboarding_challenge_backend.eCommerce.dto.common.PaginationDto;
import com.wanted_preonboarding_challenge_backend.eCommerce.dto.SummaryDto;
import com.wanted_preonboarding_challenge_backend.eCommerce.dto.review.ReviewItemDto;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ReviewGetResponse {
    private List<ReviewItemDto> items;
    private SummaryDto summary;
    private PaginationDto pagination;
}