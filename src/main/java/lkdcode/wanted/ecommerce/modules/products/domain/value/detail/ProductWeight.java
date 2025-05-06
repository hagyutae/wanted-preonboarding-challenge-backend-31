package lkdcode.wanted.ecommerce.modules.products.domain.value.detail;

import java.math.BigDecimal;
import java.util.Objects;

public record ProductWeight(
    BigDecimal value
) {
    public static final int INTEGER = 8;
    public static final int FRACTION = 2;
    public static final int PRECISION = INTEGER + FRACTION;
    public static final int SCALE = FRACTION;

    public ProductWeight(BigDecimal value) {
        this.value = Objects.requireNonNull(value);

        if (value.precision() > PRECISION) throw new IllegalArgumentException();
        if (value.scale() > SCALE) throw new IllegalArgumentException();
    }
}