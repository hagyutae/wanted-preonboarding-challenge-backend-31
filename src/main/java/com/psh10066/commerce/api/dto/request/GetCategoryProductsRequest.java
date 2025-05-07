package com.psh10066.commerce.api.dto.request;

import com.psh10066.commerce.api.common.PaginationRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class GetCategoryProductsRequest extends PaginationRequest {

    private Boolean includeSubcategories = true;
}