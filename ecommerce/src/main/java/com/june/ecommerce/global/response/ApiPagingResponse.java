package com.june.ecommerce.global.response;

import java.util.List;
import java.util.Map;

public class ApiPagingResponse<T> extends ApiResponse<Map<String, Object>> {

    public ApiPagingResponse(List<T> items, Pagination pagination) {
        super(Map.of("items", items, "pagination", pagination), "요청이 성공적으로 처리되었습니다.");
    }
}
