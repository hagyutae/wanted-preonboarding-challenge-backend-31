package com.psh10066.commerce.domain.model.product;

public interface ProductRepository {

    Product save(Product product);
    ProductDetail saveProductDetail(ProductDetail productDetail);
    ProductPrice saveProductPrice(ProductPrice productPrice);
    ProductCategory saveProductCategory(ProductCategory productCategory);
    ProductOptionGroup saveProductOptionGroup(ProductOptionGroup productOptionGroup);
    ProductOption saveProductOption(ProductOption productOption);
    ProductImage saveProductImage(ProductImage productImage);
    ProductTag saveProductTag(ProductTag productTag);

    ProductOption getProductOptionById(Long id);
}
