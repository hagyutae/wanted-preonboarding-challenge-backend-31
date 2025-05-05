package com.example.cqrsapp.product.domain;

import lombok.Getter;

public enum ProductStatus {
    ACTIVE("판매중 "),
    OUT_OF_STOCK("품절 "),
    DELETED("삭제됨");
    @Getter
    private final String description;

    ProductStatus(String description) {
        this.description = description;
    }
}
