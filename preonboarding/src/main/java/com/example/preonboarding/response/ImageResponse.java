package com.example.preonboarding.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter

@NoArgsConstructor
public class ImageResponse {
    private String url;
    private String altText;
    @Builder
    public ImageResponse(String url, String altText) {
        this.url = url;
        this.altText = altText;
    }
}
