package com.example.demo.category;

import com.example.demo.common.exception.BusinessError;
import lombok.Getter;

@Getter
public enum CategoryError implements BusinessError {
    CATEGORY_NOR_FOUND("카테고리가 존재하지 않습니다");

    private final String detailMessage;

    CategoryError(String detailMessage) {
        this.detailMessage = detailMessage;
    }
}
