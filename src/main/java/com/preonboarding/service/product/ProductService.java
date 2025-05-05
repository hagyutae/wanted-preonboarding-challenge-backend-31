package com.preonboarding.service.product;

import com.preonboarding.domain.*;
import com.preonboarding.dto.request.product.*;
import com.preonboarding.dto.request.review.ProductReviewRequestDto;
import com.preonboarding.dto.response.product.ProductImageResponse;
import com.preonboarding.dto.response.product.ProductOptionResponse;
import com.preonboarding.dto.response.product.ProductPageResponse;
import com.preonboarding.dto.response.product.ProductResponse;
import com.preonboarding.dto.response.review.ProductReviewPageResponse;
import com.preonboarding.dto.response.review.ReviewSummaryResponse;
import com.preonboarding.global.response.BaseResponse;
import com.preonboarding.global.response.paging.ProductPageBaseResponse;
import com.preonboarding.global.response.paging.ReviewPageBaseResponse;

import java.util.List;

public interface ProductService {
    ProductPageBaseResponse<ProductPageResponse> getProduct(ProductSearchRequestDto dto);
    BaseResponse<ProductResponse> createProduct(ProductCreateRequestDto dto, List<ProductCategory> productCategoryList, List<ProductTag> productTagList,
                                                Seller seller, Brand brand);
    BaseResponse<ProductResponse> editProduct(Long id, ProductEditRequestDto productEditRequestDto, List<ProductCategory> productCategoryList, List<ProductTag> productTagList,
                                              Seller seller, Brand brand);
    BaseResponse<ProductResponse> deleteProduct(Long id);

    BaseResponse<ProductOptionResponse> addProductOption(Long id, ProductOptionAddRequestDto dto);
    BaseResponse<ProductOptionResponse> editProductOption(Long id, Long optionId , ProductOptionRequestDto dto);
    BaseResponse<ProductOptionResponse> deleteProductOption(Long id, Long optionId);

    BaseResponse<ProductImageResponse> addProductImage(Long id, ProductImageRequestDto dto);

    ReviewPageBaseResponse<ProductReviewPageResponse, ReviewSummaryResponse> getProductReviews(Long id, Integer page, Integer perPage, String sort, Integer rating);
    BaseResponse<ProductReviewPageResponse> addProductReview(Long id, ProductReviewRequestDto dto);
}
