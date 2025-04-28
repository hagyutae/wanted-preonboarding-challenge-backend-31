package com.example.preonboarding.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ImageResponse {
    private String url;
    private String altText;

    public ImageResponse(String url, String altText) {
        this.url = url;
        this.altText = altText;
    }
}
