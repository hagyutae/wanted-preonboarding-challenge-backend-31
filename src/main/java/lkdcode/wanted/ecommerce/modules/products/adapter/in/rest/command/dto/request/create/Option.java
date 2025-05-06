package lkdcode.wanted.ecommerce.modules.products.adapter.in.rest.command.dto.request.create;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lkdcode.wanted.ecommerce.modules.products.domain.model.create.ProductOptionModel;
import lkdcode.wanted.ecommerce.modules.products.domain.value.option.*;

import java.math.BigDecimal;

public record Option(
    @NotNull
    @Size(max = ProductOptionName.LENGTH)
    String name,

    @Digits(integer = ProductOptionAdditionalPrice.INTEGER, fraction = ProductOptionAdditionalPrice.FRACTION)
    BigDecimal additionalPrice,

    @Size(max = ProductOptionSku.LENGTH)
    String sku,

    Integer stock,

    Integer displayOrder
) {

    public ProductOptionModel getProductOptionModel() {
        return new ProductOptionModel(
            new ProductOptionName(name),
            new ProductOptionAdditionalPrice(additionalPrice),
            new ProductOptionSku(sku),
            new ProductOptionStock(stock),
            new ProductOptionDisplayOrder(displayOrder)
        );
    }
}
