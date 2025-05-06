package lkdcode.wanted.ecommerce.modules.products.domain.value.option;

import java.math.BigDecimal;
import java.util.Objects;

public record ProductOptionAdditionalPrice(
    BigDecimal value
) {
    public static final int INTEGER = 8;
    public static final int FRACTION = 2;
    public static final int PRECISION = INTEGER + FRACTION;
    public static final int SCALE = FRACTION;
    public static final BigDecimal DEFAULT = BigDecimal.ZERO;

    public static final String INVALID_FORMAT_MESSAGE = "추가 가격은 소수점 이하 2자리까지, 최대 10자리 숫자까지 입력할 수 있습니다.";

    public ProductOptionAdditionalPrice(BigDecimal value) {
        this.value = Objects.requireNonNull(value);
        if (value.precision() > PRECISION) throw new IllegalArgumentException(INVALID_FORMAT_MESSAGE);
        if (value.scale() > SCALE) throw new IllegalArgumentException(INVALID_FORMAT_MESSAGE);
    }

    public static ProductOptionAdditionalPrice init() {
        return new ProductOptionAdditionalPrice(DEFAULT);
    }
}
