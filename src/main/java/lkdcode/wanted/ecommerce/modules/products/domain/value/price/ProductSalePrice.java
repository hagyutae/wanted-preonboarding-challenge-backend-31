package lkdcode.wanted.ecommerce.modules.products.domain.value.price;

import java.math.BigDecimal;
import java.util.Objects;

public record ProductSalePrice(
    BigDecimal value
) {

    public static final int INTEGER = 10;
    public static final int FRACTION = 2;
    public static final int PRECISION = INTEGER + FRACTION;
    public static final int SCALE = FRACTION;

    public ProductSalePrice(BigDecimal value) {
        this.value = Objects.requireNonNull(value);
        if (value.scale() > SCALE) throw new IllegalArgumentException();
        if (value.precision() > PRECISION) throw new IllegalArgumentException();
    }
}