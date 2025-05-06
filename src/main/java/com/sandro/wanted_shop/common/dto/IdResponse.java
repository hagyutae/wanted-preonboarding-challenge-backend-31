package com.sandro.wanted_shop.common.dto;

public record IdResponse(
        Long id
) {
    public static IdResponse of(Long id) {
        return new IdResponse(id);
    }
}
