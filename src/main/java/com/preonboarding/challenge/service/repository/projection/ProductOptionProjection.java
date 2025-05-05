package com.preonboarding.challenge.service.repository.projection;

public interface ProductOptionProjection {
    Long getId();
    String getName();
    java.math.BigDecimal getAdditionalPrice();
    String getSku();
    Integer getStock();
    Integer getDisplayOrder();
    Long getOptionGroupId();
}
