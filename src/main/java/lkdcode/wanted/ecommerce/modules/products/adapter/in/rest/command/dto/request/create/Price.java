package lkdcode.wanted.ecommerce.modules.products.adapter.in.rest.command.dto.request.create;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lkdcode.wanted.ecommerce.modules.products.domain.model.create.ProductPriceModel;
import lkdcode.wanted.ecommerce.modules.products.domain.value.price.*;

import java.math.BigDecimal;

public record Price(
    @JsonProperty("base_price")
    @NotNull(message = "기본 가격은 필수입니다.")
    @DecimalMin(value = ProductBasePrice.MIN_STRING, inclusive = false, message = "기본 가격은 0보다 커야 합니다.")
    @Digits(integer = ProductBasePrice.INTEGER, fraction = ProductBasePrice.FRACTION)
    BigDecimal basePrice,

    @Digits(integer = ProductSalePrice.INTEGER, fraction = ProductSalePrice.FRACTION)
    BigDecimal salePrice,

    @Digits(integer = ProductCostPrice.INTEGER, fraction = ProductCostPrice.FRACTION)
    BigDecimal costPrice,

    @Size(max = ProductCurrency.LENGTH)
    String currency,

    @Digits(integer = ProductTaxRate.INTEGER, fraction = ProductTaxRate.FRACTION)
    BigDecimal taxRate
) {

    public ProductPriceModel getProductPriceModel() {
        return new ProductPriceModel(
            new ProductBasePrice(basePrice),
            new ProductSalePrice(salePrice),
            new ProductCostPrice(costPrice),
            new ProductCurrency(currency),
            new ProductTaxRate(taxRate)
        );
    }
}