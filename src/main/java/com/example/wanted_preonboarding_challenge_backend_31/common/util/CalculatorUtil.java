package com.example.wanted_preonboarding_challenge_backend_31.common.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CalculatorUtil {

    public static Integer calculateDiscountPercentage(BigDecimal basePrice, BigDecimal salePrice) {
        return basePrice.subtract(salePrice)
                .divide(basePrice, 2, RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(100))
                .intValue();
    }
}
