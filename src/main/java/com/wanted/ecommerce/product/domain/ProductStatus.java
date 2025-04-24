package com.wanted.ecommerce.product.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ProductStatus {
    ACTIVE("ACTIVE"),
    SOLD_OUT("SOLD_OUT"),
    DELETED("DELETED");

    private final String name;
}
