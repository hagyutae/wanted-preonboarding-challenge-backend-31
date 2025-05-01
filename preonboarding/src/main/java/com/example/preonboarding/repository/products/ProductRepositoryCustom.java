package com.example.preonboarding.repository.products;

import com.example.preonboarding.domain.Brands;
import com.example.preonboarding.domain.ProductDetails;
import com.example.preonboarding.domain.Products;
import com.example.preonboarding.domain.Sellers;
import com.example.preonboarding.request.ProductSearchRequest;
import com.example.preonboarding.dto.ProductsDTO;

import java.util.List;

public interface ProductRepositoryCustom {
    List<ProductsDTO> searchPage(ProductSearchRequest search);

    //ProductsDTO findProductsById(Long id);
    Products findProductsById(Long id);

    Brands findProductBrandById(Long id);
    Sellers findProductSellerById(Long id);

}
