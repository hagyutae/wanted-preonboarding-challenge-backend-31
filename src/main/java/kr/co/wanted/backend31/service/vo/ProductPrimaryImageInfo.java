package kr.co.wanted.backend31.service.vo;

import java.util.Objects;

public record ProductPrimaryImageInfo(String url, String altText)  {

    public ProductPrimaryImageInfo {
        Objects.requireNonNull(url);
        Objects.requireNonNull(altText);
    }

}
