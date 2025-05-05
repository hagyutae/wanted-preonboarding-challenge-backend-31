package com.preonboarding.challenge.service.repository.projection;

public interface ProductImageProjection {
    Long getId();
    String getUrl();
    String getAltText();
    Boolean getIsPrimary();
    Integer getDisplayOrder();
    Long getProductId();
    Long getOptionId();
}