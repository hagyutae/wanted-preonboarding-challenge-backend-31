package kr.co.wanted.backend31.service.vo;

import java.util.Objects;

public record ProductBrandInfo(long id, String name)  {

    public ProductBrandInfo {
        Objects.requireNonNull(name);
    }

}
