package com.wanted.ecommerce.common.response;

import java.util.Map;
import lombok.Builder;
import org.springframework.http.HttpStatusCode;

@Builder
public record ErrorDetailResponse(
    HttpStatusCode code,
    String message,
    Map<String, String> details
) {
    public static ErrorDetailResponse of(HttpStatusCode code, String message, Map<String, String> details){
        return ErrorDetailResponse.builder()
            .code(code)
            .message(message)
            .details(details)
            .build();
    }
}
