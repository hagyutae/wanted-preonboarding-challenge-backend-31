package com.psh10066.commerce.api.dto.request;

import com.psh10066.commerce.api.common.PaginationRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class GetProductReviewsRequest extends PaginationRequest {

    private Integer rating;
}