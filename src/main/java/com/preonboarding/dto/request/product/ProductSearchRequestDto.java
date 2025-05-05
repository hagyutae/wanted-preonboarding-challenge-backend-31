package com.preonboarding.dto.request.product;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductSearchRequestDto {
    private Integer page;
    private Integer perPage;
    private String sort;
    private String status;
    private Integer minPrice;
    private Integer maxPrice;
    private List<Long> category;
    private Long seller;
    private Long brand;
    private String search;
}
