package sample.challengewanted.dto.product;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Getter
@NoArgsConstructor
public class ProductPriceResponse {

    private BigDecimal basePrice;
    private BigDecimal salePrice;
    private String currency;
    private BigDecimal taxRate;
    private Integer discountPercentage;

    @QueryProjection
    public ProductPriceResponse(BigDecimal basePrice, BigDecimal salePrice, String currency, BigDecimal taxRate) {
        this.basePrice = basePrice;
        this.salePrice = salePrice;
        this.currency = currency;
        this.taxRate = taxRate;
        this.discountPercentage = getDiscountPercentage();
    }

    private int getDiscountPercentage() {
        if (basePrice == null || salePrice == null || basePrice.compareTo(BigDecimal.ZERO) == 0) {
            return 0;
        }

        // ((basePrice - salePrice) / basePrice) * 100
        return basePrice.subtract(salePrice)
                .divide(basePrice, 4, RoundingMode.HALF_UP)  // 소수점 4자리까지 정확하게 계산
                .multiply(BigDecimal.valueOf(100))
                .setScale(0, RoundingMode.HALF_UP)           // 정수로 반올림
                .intValue();
    }

}