package com.dino.cqrs_challenge.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ProductStatus {

    ACTIVE("판매중"),
    OUT_OF_STOCK("품절"),
    DELETED("삭제됨");

    private final String description;
}
