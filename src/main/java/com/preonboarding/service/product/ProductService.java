package com.preonboarding.service.product;

import com.preonboarding.domain.*;
import com.preonboarding.dto.request.product.*;
import com.preonboarding.dto.request.review.ProductReviewRequestDto;
import com.preonboarding.dto.response.product.ProductImageResponse;
import com.preonboarding.dto.response.product.ProductOptionResponse;
import com.preonboarding.dto.response.product.ProductResponse;
import com.preonboarding.dto.response.review.ProductReviewResponse;
import com.preonboarding.dto.response.review.SummaryResponse;
import com.preonboarding.global.response.BaseResponse;
import com.preonboarding.global.response.paging.PageBaseResponse;

import java.util.List;

public interface ProductService {
    BaseResponse<ProductResponse> createProduct(ProductCreateRequestDto dto, List<ProductCategory> productCategoryList, List<ProductTag> productTagList,
                                                Seller seller, Brand brand);
    BaseResponse<ProductResponse> editProduct(Long id, ProductEditRequestDto productEditRequestDto, List<ProductCategory> productCategoryList, List<ProductTag> productTagList,
                                              Seller seller, Brand brand);
    BaseResponse<ProductResponse> deleteProduct(Long id);

    BaseResponse<ProductOptionResponse> addProductOption(Long id, ProductOptionAddRequestDto dto);
    BaseResponse<ProductOptionResponse> editProductOption(Long id, Long optionId , ProductOptionRequestDto dto);
    BaseResponse<ProductOptionResponse> deleteProductOption(Long id, Long optionId);

    BaseResponse<ProductImageResponse> addProductImage(Long id, ProductImageRequestDto dto);

    PageBaseResponse<ProductReviewResponse, SummaryResponse> getProductReviews(Long id,Integer page,Integer perPage,String sort,Integer rating);
    BaseResponse<ProductReviewResponse> addProductReview(Long id, ProductReviewRequestDto dto);
}
