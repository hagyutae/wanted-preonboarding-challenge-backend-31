package com.psh10066.commerce.api.dto.request;

import com.psh10066.commerce.api.common.PaginationRequest;
import com.psh10066.commerce.domain.model.product.ProductStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class GetAllProductsRequest extends PaginationRequest {

    private ProductStatus status;
    private Integer minPrice;
    private Integer maxPrice;
    private List<Long> category;
    private Long seller;
    private Long brand;
    private Boolean inStock;
    private String search;
}