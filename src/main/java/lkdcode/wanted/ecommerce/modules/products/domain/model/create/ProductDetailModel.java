package lkdcode.wanted.ecommerce.modules.products.domain.model.create;

import lkdcode.wanted.ecommerce.modules.products.domain.value.detail.*;

import java.util.Objects;

public record ProductDetailModel(
    ProductWeight weight,
    ProductDimension dimension,
    ProductMaterial material,
    ProductCountryOfOrigin countryOfOrigin,
    ProductWarrantyInfo warrantyInfo,
    ProductCareInstructions careInstructions,
    ProductAdditionalInfo additionalInfo
) {
    public ProductDetailModel(ProductWeight weight, ProductDimension dimension, ProductMaterial material, ProductCountryOfOrigin countryOfOrigin, ProductWarrantyInfo warrantyInfo, ProductCareInstructions careInstructions, ProductAdditionalInfo additionalInfo) {
        this.weight = Objects.requireNonNull(weight);
        this.dimension = Objects.requireNonNull(dimension);
        this.material = Objects.requireNonNull(material);
        this.countryOfOrigin = Objects.requireNonNull(countryOfOrigin);
        this.warrantyInfo = Objects.requireNonNull(warrantyInfo);
        this.careInstructions = Objects.requireNonNull(careInstructions);
        this.additionalInfo = Objects.requireNonNull(additionalInfo);
    }
}
