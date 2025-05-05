package sample.challengewanted.api.controller.product.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@ToString
@NoArgsConstructor
public class ProductPriceRequest {

    @Positive(message = "기본 가격은 0보다 커야 합니다.")
    private BigDecimal basePrice;

    private BigDecimal salePrice;
    private BigDecimal costPrice;
    private String currency;
    private BigDecimal taxRate;

    @Builder
    private ProductPriceRequest(BigDecimal basePrice, BigDecimal salePrice, BigDecimal costPrice,
                                String currency, BigDecimal taxRate) {
        this.basePrice = basePrice;
        this.salePrice = salePrice;
        this.costPrice = costPrice;
        this.currency = currency;
        this.taxRate = taxRate;
    }

}