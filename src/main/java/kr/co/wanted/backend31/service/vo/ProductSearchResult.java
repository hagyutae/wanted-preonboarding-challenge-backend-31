package kr.co.wanted.backend31.service.vo;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import lombok.Builder;

@Builder
public record ProductSearchResult(List<ProductSearchInfo> items, Pagenation pagenation) {

    public ProductSearchResult {
        items = Collections.unmodifiableList(items);
        items.forEach(Objects::requireNonNull);
        Objects.requireNonNull(pagenation);
    }

}
