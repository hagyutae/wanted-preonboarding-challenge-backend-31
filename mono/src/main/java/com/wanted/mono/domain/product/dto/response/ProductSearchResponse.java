package com.wanted.mono.domain.product.dto.response;

import com.wanted.mono.global.common.Pagination;
import com.wanted.mono.domain.product.dto.ProductSearchItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductSearchResponse {
    private List<ProductSearchItem> items;
    private Pagination pagination;
}
