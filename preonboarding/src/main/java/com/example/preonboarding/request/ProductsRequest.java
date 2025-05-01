package com.example.preonboarding.request;

import com.example.preonboarding.dto.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProductsRequest {
    @NotEmpty(message="상품명은 필수 항목입니다.")
    private String name;
    private String slug;
    private String shortDescription;
    private String fullDescription;
    private Long sellerId;
    private Long brandId;
    private String status;
    private DetailDTO detail;
    @Valid
    private PriceDTO price;
    private List<CategoryRequest> categories;
    private List<OptionGroupRequest> optionGroups;
    private List<ProductImageDTO> images;
    private List<Long> tags;

}
