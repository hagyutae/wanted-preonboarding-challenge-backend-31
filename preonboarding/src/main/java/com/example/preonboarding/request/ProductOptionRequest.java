package com.example.preonboarding.request;

import lombok.Data;

@Data
public class ProductOptionRequest {

    private Long optionGroupId;
    private String name;
    private double additionalPrice;
    private String sku;
    private int stock;
    private int displayOrder;
}
