package com.example.preonboarding.products.repository;

import com.example.preonboarding.brands.domain.Brands;
import com.example.preonboarding.products.domain.Products;
import com.example.preonboarding.seller.domain.Sellers;
import com.example.preonboarding.request.ProductSearchRequest;
import com.example.preonboarding.products.dto.ProductsDTO;

import java.util.List;

public interface ProductRepositoryCustom {
    List<ProductsDTO> searchPage(ProductSearchRequest search);

    //ProductsDTO findProductsById(Long id);
    Products findProductsById(Long id);

    Brands findProductBrandById(Long id);
    Sellers findProductSellerById(Long id);

}
