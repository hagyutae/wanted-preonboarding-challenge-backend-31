package lkdcode.wanted.ecommerce.modules.products.domain.value.price;

import java.math.BigDecimal;
import java.util.Objects;

public record ProductBasePrice(
    BigDecimal value
) {
    public static final BigDecimal MIN = BigDecimal.ZERO;
    public static final String MIN_STRING = "0";

    public static final int INTEGER = 10;
    public static final int FRACTION = 2;
    public static final int PRECISION = INTEGER + FRACTION;
    public static final int SCALE = FRACTION;

    public ProductBasePrice(BigDecimal value) {
        this.value = Objects.requireNonNull(value);
        if (value.compareTo(MIN) <= 0) throw new IllegalArgumentException();
        if (value.scale() > SCALE) throw new IllegalArgumentException();
        if (value.precision() > PRECISION) throw new IllegalArgumentException();
    }
}