package com.wanted_preonboarding_challenge_backend.eCommerce.dto.Image;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ImageDto {
    private String url;

    @JsonProperty("alt_text")
    private String altText;
}