package com.june.ecommerce.dto.product;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductSearchCondition {

    private String tag;
    private String startDate;
    private String endDate;
    private int sellerId;
    private int brandId;
    private int categoryId;
    private boolean inStock;

    private int minPrice;
    private int maxPrice;

    private String sortBy = "createdAt";
    private String direction = "desc";

    private int page = 0;
    private int size = 20;

}
