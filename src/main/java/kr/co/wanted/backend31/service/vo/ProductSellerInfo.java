package kr.co.wanted.backend31.service.vo;

import java.util.Objects;

public record ProductSellerInfo(long id, String name) {

    public ProductSellerInfo {
        Objects.requireNonNull(name);
    }

}
