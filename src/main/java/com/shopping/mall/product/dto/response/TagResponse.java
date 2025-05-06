package com.shopping.mall.product.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TagResponse {
    private Long id;
    private String name;
    private String slug;
}
