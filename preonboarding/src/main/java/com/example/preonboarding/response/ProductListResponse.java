package com.example.preonboarding.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProductListResponse {

    private List<ProductsDetailResponse> items;
    private PaginationResponse pagination;
}
