package com.preonboarding.service.product;

import com.preonboarding.domain.*;
import com.preonboarding.dto.request.ProductCreateRequestDto;
import com.preonboarding.dto.request.ProductEditRequestDto;
import com.preonboarding.dto.request.ProductOptionAddRequestDto;
import com.preonboarding.dto.request.ProductOptionRequestDto;
import com.preonboarding.dto.response.ProductOptionResponse;
import com.preonboarding.dto.response.ProductResponse;
import com.preonboarding.global.response.BaseResponse;

import java.util.List;

public interface ProductService {
    BaseResponse<ProductResponse> createProduct(ProductCreateRequestDto dto, List<ProductCategory> productCategoryList, List<ProductTag> productTagList,
                                                Seller seller, Brand brand);
    BaseResponse<ProductResponse> editProduct(Long id, ProductEditRequestDto productEditRequestDto,List<ProductCategory> productCategoryList,List<ProductTag> productTagList,
                                              Seller seller,Brand brand);
    BaseResponse<ProductResponse> deleteProduct(Long id);

    BaseResponse<ProductOptionResponse> addProductOption(Long id, ProductOptionAddRequestDto dto);
    BaseResponse<ProductOptionResponse> editProductOption(Long id,Long optionId ,ProductOptionRequestDto dto);
}
