package com.psh10066.commerce.domain.model.product;

import com.psh10066.commerce.api.dto.request.GetAllProductsRequest;
import com.psh10066.commerce.api.dto.response.GetAllProductsResponse;
import org.springframework.data.domain.Page;

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

    Page<GetAllProductsResponse> getAllProducts(GetAllProductsRequest request);
}
