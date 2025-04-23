package com.wanted.ecommerce.product.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

@Getter
@AllArgsConstructor
public enum ProductStatus {
    ACTIVE(1, "ACTIVE"),
    SOLD_OUT(2, "SOLD_OUT"),
    DELETED(3, "DELETED");

    private final Integer id;
    private final String name;

    public static String fromId(Integer id) {
        for(ProductStatus status : values()){
            if(status.getId().equals(id)){
                return status.getName();
            }
        }
        return StringUtils.EMPTY;
    }
}
