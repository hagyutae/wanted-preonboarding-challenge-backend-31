package com.shopping.mall.product.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class ProductTagCreateRequest {
    private List<Long> tagIds;
}
