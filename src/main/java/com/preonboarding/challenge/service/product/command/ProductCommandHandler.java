package com.preonboarding.challenge.service.product.command;

import com.preonboarding.challenge.service.product.ProductDto;

public interface ProductCommandHandler {

    // 상품 관리
    ProductDto.Product createProduct(ProductCommand.CreateProduct command);

    ProductDto.Product updateProduct(ProductCommand.UpdateProduct command);

    void deleteProduct(ProductCommand.DeleteProduct command);

    // 옵션 관리
    ProductDto.Option addProductOption(ProductCommand.AddProductOption command);

    ProductDto.Option updateProductOption(ProductCommand.UpdateProductOption command);

    void deleteProductOption(ProductCommand.DeleteProductOption command);

    // 이미지 관리
    ProductDto.Image addProductImage(ProductCommand.AddProductImage command);
}