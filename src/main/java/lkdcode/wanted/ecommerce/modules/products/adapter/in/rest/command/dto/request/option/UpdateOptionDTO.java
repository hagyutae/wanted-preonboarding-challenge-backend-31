package lkdcode.wanted.ecommerce.modules.products.adapter.in.rest.command.dto.request.option;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lkdcode.wanted.ecommerce.modules.products.domain.entity.ProductOptionId;
import lkdcode.wanted.ecommerce.modules.products.domain.model.option.UpdateOptionModel;
import lkdcode.wanted.ecommerce.modules.products.domain.value.option.*;

import java.math.BigDecimal;

public record UpdateOptionDTO(
    @NotEmpty(message = "옵션명은 필수입니다.")
    @Size(max = ProductOptionName.LENGTH, message = ProductOptionName.LENGTH_ERROR_MESSAGE)
    String name,

    @Digits(
        integer = ProductOptionAdditionalPrice.INTEGER,
        fraction = ProductOptionAdditionalPrice.FRACTION,
        message = ProductOptionAdditionalPrice.INVALID_FORMAT_MESSAGE
    )
    BigDecimal additionalPrice,

    @Size(max = ProductOptionSku.LENGTH, message = ProductOptionSku.LENGTH_ERROR_MESSAGE)
    String sku,

    @PositiveOrZero(message = "재고는 음수가 될 수 없습니다.")
    Integer stock,

    @PositiveOrZero(message = "화면 정렬순은 0 또는 양수만 가능합니다.")
    Integer display_order
) {

    public UpdateOptionModel getAddOptionModel(ProductOptionId optionId) {
        return new UpdateOptionModel(
            optionId,
            getProductOptionName(),
            getProductOptionAdditionalPrice(),
            getProductOptionSku(),
            getProductOptionStock(),
            getProductOptionDisplayOrder()
        );
    }


    private ProductOptionName getProductOptionName() {
        return new ProductOptionName(this.name);
    }

    private ProductOptionAdditionalPrice getProductOptionAdditionalPrice() {
        return new ProductOptionAdditionalPrice(additionalPrice);
    }

    private ProductOptionSku getProductOptionSku() {
        return new ProductOptionSku(sku);
    }

    private ProductOptionStock getProductOptionStock() {
        return new ProductOptionStock(stock);
    }

    private ProductOptionDisplayOrder getProductOptionDisplayOrder() {
        return new ProductOptionDisplayOrder(display_order);
    }
}