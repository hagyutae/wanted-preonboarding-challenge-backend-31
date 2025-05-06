package lkdcode.wanted.ecommerce.modules.products.adapter.in.rest.command.dto.request.create;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Size;
import lkdcode.wanted.ecommerce.modules.products.domain.model.create.ProductDetailModel;
import lkdcode.wanted.ecommerce.modules.products.domain.value.detail.*;

import java.math.BigDecimal;
import java.util.Map;

public record Detail(
    @Digits(integer = ProductWeight.INTEGER, fraction = ProductWeight.FRACTION)
    BigDecimal weight,
    Map<String, Object> dimensions,
    String materials,

    @Size(max = ProductCountryOfOrigin.LENGTH, message = "원산지는 최대 100자까지 가능합니다.")
    String countryOfOrigin,
    String warrantyInfo,
    String careInstructions,
    Map<String, Object> additionalInfo
) {

    public ProductDetailModel getProductDetailModel() {
        return new ProductDetailModel(
            new ProductWeight(weight),
            new ProductDimension(dimensions),
            new ProductMaterial(materials),
            new ProductCountryOfOrigin(countryOfOrigin),
            new ProductWarrantyInfo(warrantyInfo),
            new ProductCareInstructions(careInstructions),
            new ProductAdditionalInfo(additionalInfo)
        );
    }
}