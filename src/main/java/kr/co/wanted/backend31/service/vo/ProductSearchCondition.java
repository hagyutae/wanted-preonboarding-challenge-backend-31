package kr.co.wanted.backend31.service.vo;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.data.domain.Pageable;

import lombok.Builder;

@Builder
public record ProductSearchCondition(Pageable pageable, Optional<String> status, Optional<BigDecimal> minPrice,
        Optional<BigDecimal> maxPrice, List<Long> categories, Optional<Long> seller, Optional<Long> brand,
        Optional<Boolean> inStock, Optional<String> search) {
    public ProductSearchCondition {
        Objects.requireNonNull(pageable);
        Objects.requireNonNull(status);
        Objects.requireNonNull(minPrice);
        Objects.requireNonNull(maxPrice);
        categories = Collections.unmodifiableList(categories);
        categories.forEach(Objects::requireNonNull);
        Objects.requireNonNull(seller);
        Objects.requireNonNull(brand);
        Objects.requireNonNull(inStock);
        Objects.requireNonNull(search);
    }
}
