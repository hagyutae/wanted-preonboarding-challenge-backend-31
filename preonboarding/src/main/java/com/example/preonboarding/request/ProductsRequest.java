package com.example.preonboarding.request;

import com.example.preonboarding.dto.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProductsRequest {
    private String name;
    private String slug;
    private String shortDescription;
    private String fullDescription;
    private Long sellerId;
    private Long brandId;
    private String status;
    private DetailDTO detail;
    private PriceDTO price;
    private List<CategoryRequest> categories;
    private List<OptionGroupRequest> optionGroups;
    private List<ProductImageDTO> images;
    private List<Long> tags;

}
